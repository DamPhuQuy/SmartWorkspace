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
    private WorkspaceMember workspaceMember;
    private String warningType;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
}
