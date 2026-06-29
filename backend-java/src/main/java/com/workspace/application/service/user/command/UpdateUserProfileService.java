package com.workspace.application.service.user.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.user.command.UpdateUserProfileUseCase;
import com.workspace.application.port.out.user.UserRepositoryPort;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.user.UserProfile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateUserProfileService implements UpdateUserProfileUseCase {

    private final UserRepositoryPort userRepositoryPort;
    
    @Override
    @Transactional
    public UserProfile updateUserProfile(Command command) {
        // Ensure user exists
        userRepositoryPort.findById(command.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + command.userId() + " not found"));

        UserProfile existingProfile = userRepositoryPort.findProfileByUserId(command.userId())
                .orElse(UserProfile.builder().userId(command.userId()).build());

        UserProfile updatedProfile = UserProfile.builder()
                .userId(existingProfile.getUserId())
                .firstName(command.firstName() != null ? command.firstName() : existingProfile.getFirstName())
                .lastName(command.lastName() != null ? command.lastName() : existingProfile.getLastName())
                .avatarUrl(command.avatarUrl() != null ? command.avatarUrl() : existingProfile.getAvatarUrl())
                .bio(command.bio() != null ? command.bio() : existingProfile.getBio())
                .discordId(command.discordId() != null ? command.discordId() : existingProfile.getDiscordId())
                .build();

        return userRepositoryPort.saveProfile(updatedProfile);
    }
}
