package com.workspace.domain.model.user;

import java.time.Instant;
import java.util.UUID;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    private UUID id;
    private UserProfile userProfile;
    private String email;
    private String passwordHash;
    private String phone;
    private boolean onlineStatus;
    private Instant createdAt;
    private Instant updatedAt;

    public User(UUID id, String email, String passwordHash) {
        if (id == null) {
            throw new IllegalArgumentException("User id is required");
        }

        if (isValidEmail(email)) {
            throw new IllegalArgumentException("Email is invalid");
        }

        if (isBlank(passwordHash)) {
            throw new IllegalArgumentException("Password hash is required");
        }

        this.id = id;
        this.email = email.trim().toLowerCase();
        this.passwordHash = passwordHash;
        this.onlineStatus = false;
    }

    public void changeEmail(String email) {
        if (isValidEmail(email)) {
            throw new IllegalArgumentException("Email is invalid");
        }

        this.email = email.trim().toLowerCase();
    }

    public void changePasswordHash(String passwordHash) {
        if (isBlank(passwordHash)) {
            throw new IllegalArgumentException("Password hash is required");
        }

        this.passwordHash = passwordHash;
    }

    public void changePhone(String phone) {
        if (isBlank(phone)) {
            this.phone = null;
            return;
        }

        this.phone = phone.trim();
    }

    public void markOnline() {
        this.onlineStatus = true;
    }

    public void markOffline() {
        this.onlineStatus = false;
    }

    private boolean isValidEmail(String email) {
        return email == null || !EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    private boolean isBlank(String input) {
        return input == null || input.isBlank();
    }
}
