package com.workspace.domain.model.role;

import java.time.Instant;
import java.util.UUID;
import com.workspace.domain.model.workspace.Workspace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private UUID id;
    private Workspace workspace;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
}
