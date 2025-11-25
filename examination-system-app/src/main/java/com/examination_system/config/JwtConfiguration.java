package com.examination_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.examination_system.core.security.JwtService;

@Configuration
public class JwtConfiguration {

    @Bean
    public JwtService jwtService() {
        return new JwtService();
    }
}
