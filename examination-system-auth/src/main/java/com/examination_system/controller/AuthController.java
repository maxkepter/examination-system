package com.examination_system.controller;

import com.examination_system.core.security.JwtUtils;
import com.examination_system.model.dto.request.LoginRequest;
import com.examination_system.model.dto.request.RefreshTokenRequest;
import com.examination_system.model.dto.request.RegisterRequest;
import com.examination_system.model.dto.response.AuthRespone;
import com.examination_system.model.entity.token.RefreshToken;
import com.examination_system.model.entity.user.AuthInfo;
import com.examination_system.model.entity.user.User;
import com.examination_system.repository.user.AuthInfoRepository;
import com.examination_system.service.RefreshTokenService;

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
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthInfoRepository authInfoRepository;
    private final JwtUtils jwtService;
    private final RefreshTokenService refreshTokenService;

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

    @PostMapping("/logout")
    public ResponseEntity<String> postMethodName(@RequestBody RefreshTokenRequest request) {
        refreshTokenService.revokeToken(request.getRefreshToken());
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello, authenticated user!");
    }

}
