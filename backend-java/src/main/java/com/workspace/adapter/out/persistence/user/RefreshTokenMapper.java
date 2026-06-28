package com.workspace.adapter.out.persistence.user;

import com.workspace.domain.model.user.RefreshToken;

public class RefreshTokenMapper {

    private RefreshTokenMapper() {}

    public static RefreshToken toDomain(RefreshTokenEntity entity) {
        if (entity == null) {
            return null;
        }

        return RefreshToken.builder()
                .id(entity.getId())
                .user(UserMapper.toDomain(entity.getUser()))
                .token(entity.getToken())
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
                .token(domain.getToken())
                .expiresAt(domain.getExpiresAt())
                .revokedAt(domain.getRevokedAt())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
