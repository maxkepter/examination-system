package com.examination_system.auth.repository.token;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.examination_system.auth.model.entity.token.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.expiryDate< :now")
    void deleteByExpiryDateBefore(Instant now);

    @Modifying
    @Query("UPDATE RefreshToken rt SET rt.isRevoked = true, rt.revokedDate = :revokedDate WHERE rt.authInfo.userName = :username AND rt.isRevoked = false")
    void revokeAllUserTokens(String username, Instant revokedDate);

}
