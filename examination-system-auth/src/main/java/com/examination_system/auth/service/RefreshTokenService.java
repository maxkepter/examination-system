package com.examination_system.service;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examination_system.exception.TokenExpiredException;
import com.examination_system.model.entity.token.RefreshToken;
import com.examination_system.model.entity.user.AuthInfo;
import com.examination_system.repository.token.RefreshTokenRepository;
import com.examination_system.repository.user.AuthInfoRepository;

@Service
public class RefreshTokenService {
    @Value("${jwt.refresh-token-duration-days:30}")
    private long refreshTokenDurationDays;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthInfoRepository authInfoRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
            AuthInfoRepository authInfoRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.authInfoRepository = authInfoRepository;
    }

    // create refresh token for user
    @Transactional
    public RefreshToken createRefreshToken(String username) {
        refreshTokenRepository.revokeAllUserTokens(username, Instant.now());
        AuthInfo authInfo = authInfoRepository.findByUserName(username);
        RefreshToken refreshToken = RefreshToken.builder()
                .authInfo(authInfo)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusSeconds(refreshTokenDurationDays * 24 * 60 * 60)).build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new TokenExpiredException(MessageFormat
                    .format("Refresh token {0} was expired. Please make a new signin request", token.getToken()));
        }
        if (token.isRevoked()) {
            throw new TokenExpiredException(MessageFormat
                    .format("Refresh token {0} was revoked. Please make a new signin request", token.getToken()));
        }
        return token;
    }

    @Transactional
    public void revokeToken(String token) {
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(token);
        if (refreshTokenOpt.isPresent()) {
            RefreshToken refreshToken = refreshTokenOpt.get();
            refreshToken.setRevoked(true);
            refreshToken.setRevokedDate(Instant.now());
            refreshTokenRepository.save(refreshToken);
        }
    }

    @Transactional
    public void deleteExpiredTokens() {
        refreshTokenRepository.deleteByExpiryDateBefore(Instant.now());
    }
}
