package com.workspace.application.port.in.user;

import com.workspace.domain.model.user.User;

public interface CreateUserUseCase {
    User createUser(Command command);

    record Command(
        String email,
        String password,
        String phone,
        String firstName,
        String lastName,
        String avatarUrl,
        String bio,
        String discordId
    ) {}
}
