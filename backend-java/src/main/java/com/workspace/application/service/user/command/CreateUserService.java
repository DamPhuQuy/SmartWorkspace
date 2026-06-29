package com.workspace.application.service.user.command;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.user.command.CreateUserUseCase;
import com.workspace.application.port.out.user.UserRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.model.user.User;
import com.workspace.domain.model.user.UserProfile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    @Transactional
    public User createUser(Command command) {
        if (userRepositoryPort.existsByEmail(command.email())) {
            throw new DomainException("User with email " + command.email() + " already exists");
        }
        if (command.phone() != null && userRepositoryPort.existsByPhone(command.phone())) {
            throw new DomainException("User with phone " + command.phone() + " already exists");
        }

        UUID userId = UUID.randomUUID();

        UserProfile userProfile = UserProfile.builder()
                .userId(userId)
                .firstName(command.firstName())
                .lastName(command.lastName())
                .avatarUrl(command.avatarUrl())
                .bio(command.bio())
                .discordId(command.discordId())
                .build();

        User user = User.builder()
                .id(userId)
                .email(command.email())
                .phone(command.phone())
                .passwordHash(command.password()) // In production, this should be encoded.
                .onlineStatus(false)
                .userProfile(userProfile)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return userRepositoryPort.save(user);
    }
}
