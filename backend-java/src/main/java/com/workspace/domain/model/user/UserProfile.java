package com.workspace.domain.model.user;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String avatarUrl;
    private String bio;
    private String discordId;

    public UserProfile(UUID userId, String firstName) {
        if (userId == null) {
            throw new IllegalArgumentException("User id is required");
        }
        if (isBlank(firstName)) {
            throw new IllegalArgumentException("First name is required");
        }

        this.userId = userId;
        this.firstName = firstName;
    }

    public void changeName(String firstName, String lastName) {
        if (isBlank(firstName)) {
            throw new IllegalArgumentException("First name is required");
        }

        this.firstName = firstName;
        this.lastName = normalize(lastName);
    }

    public void changeAvatarUrl(String avatarUrl) {
        this.avatarUrl = normalize(avatarUrl);
    }

    public void changeBio(String bio) {
        this.bio = normalize(bio);
    }

    public void changeDiscordId(String discordId) {
        this.discordId = normalize(discordId);
    }

    public String getFullName() {
        if (isBlank(lastName)) {
            return firstName;
        }

        return firstName + " " + lastName;
    }

    private boolean isBlank(String input) {
        return input == null || input.isBlank();
    }

    private String normalize(String input) {
        return isBlank(input) ? null : input.trim();
    }
}
