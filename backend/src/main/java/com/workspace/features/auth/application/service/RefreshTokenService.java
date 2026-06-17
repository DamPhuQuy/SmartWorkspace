package com.workspace.features.auth.application.service;

import com.workspace.features.auth.application.dto.*;
import com.workspace.features.auth.application.service.*;
import com.workspace.features.auth.infrastructure.config.*;
import com.workspace.features.auth.infrastructure.persistence.entity.*;
import com.workspace.features.auth.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.auth.infrastructure.web.dto.*;

import com.workspace.features.auth.infrastructure.config.AuthProperties;
import com.workspace.features.auth.infrastructure.persistence.entity.RefreshToken;
import com.workspace.features.auth.infrastructure.persistence.jpaRepo.RefreshTokenRepository;
import com.workspace.features.user.infrastructure.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthProperties authProperties;
    private final SecureRandom secureRandom = new SecureRandom();

    @Transactional
    public RefreshToken createRefreshToken(User user) {
        byte[] randomBytes = new byte[64];
        secureRandom.nextBytes(randomBytes);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes))
                .user(user)
                .expiryDate(OffsetDateTime.now().plusNanos(authProperties.refreshTokenExpirationMs() * 1_000_000))
                .revoked(false)
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken validateRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Refresh token is invalid"));

        if (refreshToken.isRevoked()) {
            throw new IllegalStateException("Refresh token has been revoked");
        }
        if (refreshToken.getExpiryDate().isBefore(OffsetDateTime.now())) {
            throw new IllegalStateException("Refresh token has expired");
        }
        return refreshToken;
    }

    @Transactional
    public void revoke(String token) {
        refreshTokenRepository.findByToken(token).ifPresent(refreshToken -> {
            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);
        });
    }
}
