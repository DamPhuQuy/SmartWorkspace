package com.workspace.domain.model.assignment;

import java.time.Instant;
import java.util.UUID;
import com.workspace.domain.model.workspace.Workspace;
import com.workspace.domain.model.workspace.WorkspaceMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {
    private UUID id;
    private Workspace workspace;
    private String title;
    private String description;
    private Instant deadline;
    private WorkspaceMember createdBy;
    private Instant createdAt;
    private Instant updatedAt;
}
