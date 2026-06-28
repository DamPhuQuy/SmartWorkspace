package com.workspace.domain.model.assignment;

import java.time.Instant;
import java.util.UUID;
import com.workspace.domain.model.workspace.WorkSpace;
import com.workspace.domain.model.workspace.WorkSpaceMember;
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
    private WorkSpace workspace;
    private String title;
    private String description;
    private Instant deadline;
    private WorkSpaceMember createdBy;
    private Instant createdAt;
    private Instant updatedAt;
}
