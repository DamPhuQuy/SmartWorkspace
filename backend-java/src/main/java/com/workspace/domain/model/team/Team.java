package com.workspace.domain.model.team;

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
public class Team {
    private UUID id;
    private Workspace workspace;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
}
