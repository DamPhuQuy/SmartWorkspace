package com.workspace.application.port.in.user.command;

import java.util.UUID;
import com.workspace.domain.model.user.UserProfile;

public interface UpdateUserProfileUseCase {
    UserProfile updateUserProfile(Command command);

    record Command(
        UUID userId,
        String firstName,
        String lastName,
        String avatarUrl,
        String bio,
        String discordId
    ) {}
}
