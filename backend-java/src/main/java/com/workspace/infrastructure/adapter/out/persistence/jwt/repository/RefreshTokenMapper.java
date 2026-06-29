package com.workspace.infrastructure.adapter.out.persistence.jwt.repository;

import com.workspace.domain.model.jwt.RefreshToken;
import com.workspace.infrastructure.adapter.out.persistence.jwt.entity.RefreshTokenEntity;
import com.workspace.infrastructure.adapter.out.persistence.user.repository.UserMapper;


public class RefreshTokenMapper {

    private RefreshTokenMapper() {}

    public static RefreshToken toDomain(RefreshTokenEntity entity) {
        if (entity == null) {
            return null;
        }

        return RefreshToken.builder()
                .id(entity.getId())
                .user(UserMapper.toDomain(entity.getUser()))
                .tokenHash(entity.getTokenHash())
                .expiresAt(entity.getExpiresAt())
                .revokedAt(entity.getRevokedAt())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static RefreshTokenEntity toEntity(RefreshToken domain) {
        if (domain == null) {
            return null;
        }

        return RefreshTokenEntity.builder()
                .id(domain.getId())
                .user(UserMapper.toEntity(domain.getUser()))
                .tokenHash(domain.getTokenHash())
                .expiresAt(domain.getExpiresAt())
                .revokedAt(domain.getRevokedAt())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
