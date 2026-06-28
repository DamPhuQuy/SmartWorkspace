package com.workspace.infrastructure.adapter.in.web.mapper;

import com.workspace.domain.model.user.User;
import com.workspace.domain.model.user.UserProfile;
import com.workspace.infrastructure.adapter.in.web.dto.UserDto;

public class UserWebMapper {

    private UserWebMapper() {}

    public static UserDto.UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        return new UserDto.UserResponse(
            user.getId(),
            user.getEmail(),
            user.getPhone(),
            user.isOnlineStatus(),
            toProfileResponse(user.getUserProfile()),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }

    public static UserDto.UserProfileResponse toProfileResponse(UserProfile profile) {
        if (profile == null) {
            return null;
        }

        return new UserDto.UserProfileResponse(
            profile.getUserId(),
            profile.getFirstName(),
            profile.getLastName(),
            profile.getAvatarUrl(),
            profile.getBio(),
            profile.getDiscordId()
        );
    }
}
