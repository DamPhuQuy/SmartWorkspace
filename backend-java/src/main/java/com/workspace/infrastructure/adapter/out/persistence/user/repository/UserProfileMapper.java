package com.workspace.infrastructure.adapter.out.persistence.user.repository;

import com.workspace.domain.model.user.UserProfile;
import com.workspace.infrastructure.adapter.out.persistence.user.entity.UserProfileEntity;

public class UserProfileMapper {

    private UserProfileMapper() {}

    public static UserProfile toDomain(UserProfileEntity entity) {
        if (entity == null) {
            return null;
        }

        return UserProfile.builder()
                .userId(entity.getUserId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .avatarUrl(entity.getAvatarUrl())
                .bio(entity.getBio())
                .discordId(entity.getDiscordId())
                .build();
    }

    public static UserProfileEntity toEntity(UserProfile domain) {
        if (domain == null) {
            return null;
        }

        return UserProfileEntity.builder()
                .userId(domain.getUserId())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .avatarUrl(domain.getAvatarUrl())
                .bio(domain.getBio())
                .discordId(domain.getDiscordId())
                .build();
    }
}
