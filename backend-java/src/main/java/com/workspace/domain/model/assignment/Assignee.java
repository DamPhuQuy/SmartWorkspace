package com.workspace.domain.model.assignment;

import java.time.Instant;
import java.util.UUID;
import com.workspace.domain.model.workspace.WorkSpaceMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assignee {
    private UUID id;
    private Assignment assignment;
    private WorkSpaceMember workspaceMember;
    private Instant assignedAt;
}
