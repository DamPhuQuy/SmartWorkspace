package com.workspace.infrastructure.database.mapper.user;

import com.workspace.domain.model.user.User;
import com.workspace.domain.model.user.UserProfile;
import com.workspace.infrastructure.database.entity.user.UserEntity;
import com.workspace.infrastructure.database.entity.user.UserProfileEntity;

public class UserMapper {

    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        UserProfile userProfile = null;
        if (entity.getUserProfile() != null) {
            userProfile = UserProfile.builder()
                    .userId(entity.getUserProfile().getUserId())
                    .firstName(entity.getUserProfile().getFirstName())
                    .lastName(entity.getUserProfile().getLastName())
                    .avatarUrl(entity.getUserProfile().getAvatarUrl())
                    .bio(entity.getUserProfile().getBio())
                    .discordId(entity.getUserProfile().getDiscordId())
                    .build();
        }

        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .passwordHash(entity.getPasswordHash())
                .phone(entity.getPhone())
                .onlineStatus(entity.isOnlineStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .userProfile(userProfile)
                .build();
    }

    public static UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }

        UserEntity entity = UserEntity.builder()
                .id(domain.getId())
                .email(domain.getEmail())
                .passwordHash(domain.getPasswordHash())
                .phone(domain.getPhone())
                .onlineStatus(domain.isOnlineStatus())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();

        if (domain.getUserProfile() != null) {
            UserProfileEntity profileEntity = UserProfileEntity.builder()
                    .userId(domain.getUserProfile().getUserId())
                    .user(entity)
                    .firstName(domain.getUserProfile().getFirstName())
                    .lastName(domain.getUserProfile().getLastName())
                    .avatarUrl(domain.getUserProfile().getAvatarUrl())
                    .bio(domain.getUserProfile().getBio())
                    .discordId(domain.getUserProfile().getDiscordId())
                    .build();
            entity.setUserProfile(profileEntity);
        }

        return entity;
    }
}
