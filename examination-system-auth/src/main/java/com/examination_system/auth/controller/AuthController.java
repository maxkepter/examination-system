package com.examination_system.auth.controller;

import com.examination_system.core.security.JwtUtils;
import com.examination_system.auth.model.dto.request.LoginRequest;
import com.examination_system.auth.model.dto.request.RefreshTokenRequest;
import com.examination_system.auth.model.dto.request.RegisterRequest;
import com.examination_system.auth.model.dto.response.AuthRespone;
import com.examination_system.auth.model.entity.token.RefreshToken;
import com.examination_system.common.model.entity.user.AuthInfo;
import com.examination_system.common.model.entity.user.User;
import com.examination_system.common.repository.user.AuthInfoRepository;
import com.examination_system.auth.service.RefreshTokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * Authentication Controller - handles login, register
 */
@RestController
@Tag(name = "Xác thực", description = "API xác thực người dùng: đăng ký, đăng nhập, làm mới token")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthInfoRepository authInfoRepository;
    private final JwtUtils jwtService;
    private final RefreshTokenService refreshTokenService;
    @Operation(summary = "Đăng ký", description = "Tạo tài khoản người dùng mới")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tạo tài khoản thành công", content = @Content),
        @ApiResponse(responseCode = "400", description = "Tên đăng nhập đã tồn tại hoặc dữ liệu không hợp lệ", content = @Content)
    })

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (authInfoRepository.existsByUserName(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        // Create a new user
        User user = new User(request.getFirstName(), request.getLastName(),
                request.getEmail());
        // Create a new auth info
        AuthInfo authInfo = AuthInfo.builder()
                .userId(user.getUserId())
                .userName(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .user(user)
                .role(AuthInfo.ROLE_USER)
                .build();
        authInfoRepository.save(authInfo);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
    @Operation(summary = "Đăng nhập", description = "Xác thực và cấp token truy cập")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Đăng nhập thành công",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AuthRespone.class))),
        @ApiResponse(responseCode = "401", description = "Sai thông tin đăng nhập", content = @Content)
    })

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(auth);

            String role = auth.getAuthorities().stream().findFirst().get().getAuthority();

            String accessToken = jwtService.generateToken(request.getUsername(), role);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getUsername());

            AuthRespone authRespone = AuthRespone.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getToken()).expiresIn(refreshToken.getExpiryDate().getEpochSecond())
                    .build();

            return ResponseEntity.ok(authRespone);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }

    @Operation(summary = "Làm mới token", description = "Cấp token truy cập mới bằng refresh token hợp lệ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Thành công",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = AuthRespone.class))),
        @ApiResponse(responseCode = "401", description = "Refresh token không hợp lệ", content = @Content)
    })
    // provide new access token using refresh token
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        String token = request.getRefreshToken();
        try {
            return refreshTokenService
                    .findByToken(token)
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getAuthInfo)
                    .map(authInfo -> {
                        String newAccessToken = jwtService.generateToken(authInfo.getUserName(),
                                AuthInfo.AUTHORIES[authInfo.getRole()]);
                        AuthRespone authRespone = AuthRespone.builder()
                                .accessToken(newAccessToken)
                                .refreshToken(token)
                                .build();
                        return ResponseEntity.ok(authRespone);
                    })
                    .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }
    @Operation(summary = "Đăng xuất", description = "Thu hồi refresh token và xóa ngữ cảnh bảo mật")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Đăng xuất thành công", content = @Content)
    })

    @PostMapping("/logout")
    public ResponseEntity<String> postMethodName(@RequestBody RefreshTokenRequest request) {
        refreshTokenService.revokeToken(request.getRefreshToken());
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("Logout successful");
    }
    @Operation(summary = "Chào", description = "Kiểm tra endpoint dành cho người dùng đã xác thực")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Thành công", content = @Content)
    })

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello, authenticated user!");
    }

}
