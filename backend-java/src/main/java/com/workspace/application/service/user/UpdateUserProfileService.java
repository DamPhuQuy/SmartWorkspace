package com.workspace.application.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.user.UpdateUserProfileUseCase;
import com.workspace.application.port.out.user.UserRepositoryPort;
import com.workspace.application.port.out.user.UserProfileRepositoryPort;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.user.UserProfile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateUserProfileService implements UpdateUserProfileUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final UserProfileRepositoryPort userProfileRepositoryPort;

    @Override
    @Transactional
    public UserProfile updateUserProfile(Command command) {
        // Ensure user exists
        userRepositoryPort.findById(command.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + command.userId() + " not found"));

        UserProfile existingProfile = userProfileRepositoryPort.findByUserId(command.userId())
                .orElse(UserProfile.builder().userId(command.userId()).build());

        UserProfile updatedProfile = UserProfile.builder()
                .userId(existingProfile.getUserId())
                .firstName(command.firstName() != null ? command.firstName() : existingProfile.getFirstName())
                .lastName(command.lastName() != null ? command.lastName() : existingProfile.getLastName())
                .avatarUrl(command.avatarUrl() != null ? command.avatarUrl() : existingProfile.getAvatarUrl())
                .bio(command.bio() != null ? command.bio() : existingProfile.getBio())
                .discordId(command.discordId() != null ? command.discordId() : existingProfile.getDiscordId())
                .build();

        return userProfileRepositoryPort.save(updatedProfile);
    }
}
