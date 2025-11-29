package com.examination_system.core.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

/**
 * JWT Service - Technical component for JWT token generation and validation
 * This is a base service to be extended by application modules
 */
@Component
public class JwtUtils {
    private String secretKey = null;

    @Value("${jwt.secret:}")
    private String configuredSecret;

    @Value("${jwt.expiration-minutes:15}")
    private long expirationMinutes;

    @Value("${jwt.issuer:ses-app}")
    private String issuer;

    // Generate a new secret key if not configured
    public String generateSecretKey() {
        if (this.secretKey != null) {
            return this.secretKey;
        }
        if (configuredSecret != null && !configuredSecret.isBlank()) {
            this.secretKey = configuredSecret.trim();
            return this.secretKey;
        }
        try {
            KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey generatedKey = generator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(generatedKey.getEncoded());
            return secretKey;
        } catch (Exception e) {
            throw new IllegalStateException("Cannot generate JWT secret", e);
        }
    }

    // Generate JWT token for a given username
    public String generateToken(String username,String role) {
        Map<String, Object> claims = new HashMap<>();
        long expMillis = System.currentTimeMillis() + 1000L * 60L * expirationMinutes;
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expMillis))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        String secret = generateSecretKey();
        byte[] keyBytes;
        try {
            keyBytes = Decoders.BASE64.decode(secret);
        } catch (IllegalArgumentException e) {
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extract username from JWT token
    public String extractUsername(String token) {
        return extracClaims(token, Claims::getSubject);
    }

    private <T> T extracClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extracClaims(token, Claims::getExpiration).before(new Date());
    }
}
