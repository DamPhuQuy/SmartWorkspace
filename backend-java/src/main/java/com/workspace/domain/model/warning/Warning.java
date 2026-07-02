package com.workspace.domain.model.warning;

import java.time.Instant;
import java.util.UUID;
import com.workspace.domain.model.workspace.WorkspaceMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Warning {
    private UUID id;
    private UUID workspaceMemberId;
    private WarningType warningType;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;

    public Warning(UUID id, UUID workspaceMemberId, WarningType warningType, String description) {
        if (id == null) {
            throw new IllegalArgumentException("Warning id is required");
        }

        if (workspaceMemberId == null) {
            throw new IllegalArgumentException("Workspace member id is required");
        }

        if (warningType == null) {
            throw new IllegalArgumentException("Warning type is required");
        }

        if (isBlank(description)) {
            throw new IllegalArgumentException("Description is required");
        }

        this.id = id;
        this.workspaceMemberId = workspaceMemberId;
        this.warningType = warningType;
        this.description = description.trim();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void updateDescription(String description) {
        if (isBlank(description)) {
            throw new IllegalArgumentException("Description is required");
        }

        this.description = description.trim();
        this.updatedAt = Instant.now();
    }

    private boolean isBlank(String input) {
        return input == null || input.isBlank();
    }
}
