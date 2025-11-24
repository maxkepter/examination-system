package com.examination_system.controller.system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.examination_system.model.dto.request.auth.LoginRequest;
import com.examination_system.model.dto.request.auth.RegisterRequest;
import com.examination_system.model.entity.user.AuthInfo;
import com.examination_system.model.entity.user.User;
import com.examination_system.repository.user.AuthInfoDao;
import com.examination_system.service.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthInfoDao authInfoDao;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (authInfoDao.existsByUserName(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        System.out.println(request);
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
        authInfoDao.save(authInfo);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(auth);
            httpRequest.getSession(true); // tạo session

            return ResponseEntity.ok(jwtService.generateToken(request.getUsername()));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello, authenticated user!");
    }

}
