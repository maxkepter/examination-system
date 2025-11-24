package com.examination_system.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
// Duplicate JwtService disabled: remove @Service to avoid bean clash.

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

public class JwtService {
    private String SECRET_KEY = null;

    @Value("${jwt.secret:}")
    private String configuredSecret;

    @Value("${jwt.expiration-minutes:5}")
    private long expirationMinutes;

    @Value("${jwt.issuer:ses-app}")
    private String issuer;

    public String generateSecretKey() {
        if (this.SECRET_KEY != null) {
            return this.SECRET_KEY;
        }
        if (configuredSecret != null && !configuredSecret.isBlank()) {
            this.SECRET_KEY = configuredSecret.trim();
            return this.SECRET_KEY;
        }
        try {
            KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = generator.generateKey();
            SECRET_KEY = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            return SECRET_KEY;
        } catch (Exception e) {
            throw new IllegalStateException("Cannot generate JWT secret", e);
        }
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        long expMillis = System.currentTimeMillis() + 1000L * 60L * expirationMinutes;
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setSubject(username)
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