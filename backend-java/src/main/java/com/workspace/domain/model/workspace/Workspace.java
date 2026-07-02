package com.workspace.domain.model.workspace;

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
public class Workspace {
    private static final Pattern SLUG_PATTERN = Pattern.compile(
            "^[a-z0-9]+(?:-[a-z0-9]+)*$"
    );

    private UUID id;
    private String name;
    private String slug;
    private String description;
    private UUID ownerId;
    private Instant createdAt;
    private Instant updatedAt;

    public Workspace(UUID id, String name, String slug, UUID ownerId) {
        if (id == null) {
            throw new IllegalArgumentException("Workspace id is required");
        }

        if (isBlank(name)) {
            throw new IllegalArgumentException("Workspace name is required");
        }

        if (isValidSlug(slug)) {
            throw new IllegalArgumentException("Workspace slug is invalid");
        }

        if (ownerId == null) {
            throw new IllegalArgumentException("Workspace owner is required");
        }

        this.id = id;
        this.name = name.trim();
        this.slug = normalizeSlug(slug);
        this.ownerId = ownerId;
    }

    public void rename(String name) {
        if (isBlank(name)) {
            throw new IllegalArgumentException("Workspace name is required");
        }

        this.name = name.trim();
    }

    public void changeSlug(String slug) {
        if (isValidSlug(slug)) {
            throw new IllegalArgumentException("Workspace slug is invalid");
        }

        this.slug = normalizeSlug(slug);
    }

    public void changeDescription(String description) {
        this.description = isBlank(description) ? null : description.trim();
    }

    public boolean isOwnedBy(UUID userId) {
        return ownerId != null && ownerId.equals(userId);
    }

    private boolean isValidSlug(String slug) {
        return slug == null || !SLUG_PATTERN.matcher(slug.trim().toLowerCase()).matches();
    }

    private String normalizeSlug(String slug) {
        return slug.trim().toLowerCase();
    }

    private boolean isBlank(String input) {
        return input == null || input.isBlank();
    }
}
