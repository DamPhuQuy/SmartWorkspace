package com.workspace.domain.model.assignment;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Submission {
    private UUID id;
    private Assignee assignmentAssignee;
    private String submissionUrl;
    private Instant submittedAt;
    private Instant createdAt;
    private Instant updatedAt;
}
