package com.workspace.infrastructure.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

public final class UserDto {
    private UserDto() {}

    public record CreateUserRequest(
        String email,
        String password,
        String phone,
        String firstName,
        String lastName,
        String avatarUrl,
        String bio,
        String discordId
    ) {}

    public record UpdateUserProfileRequest(
        String firstName,
        String lastName,
        String avatarUrl,
        String bio,
        String discordId
    ) {}

    public record UserProfileResponse(
        UUID userId,
        String firstName,
        String lastName,
        String avatarUrl,
        String bio,
        String discordId
    ) {}

    public record UserResponse(
        UUID id,
        String email,
        String phone,
        boolean onlineStatus,
        UserProfileResponse profile,
        Instant createdAt,
        Instant updatedAt
    ) {}
}
