package com.workspace.infrastructure.database.mapper.assignment;

import com.workspace.domain.model.assignment.Submission;
import com.workspace.infrastructure.database.entity.assignment.SubmissionEntity;

public class SubmissionMapper {

    private SubmissionMapper() {}

    public static Submission toDomain(SubmissionEntity entity) {
        if (entity == null) {
            return null;
        }

        return Submission.builder()
                .id(entity.getId())
                .assignmentAssignee(AssigneeMapper.toDomain(entity.getAssignmentAssignee()))
                .submissionUrl(entity.getSubmissionUrl())
                .submittedAt(entity.getSubmittedAt())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static SubmissionEntity toEntity(Submission domain) {
        if (domain == null) {
            return null;
        }

        return SubmissionEntity.builder()
                .id(domain.getId())
                .assignmentAssignee(AssigneeMapper.toEntity(domain.getAssignmentAssignee()))
                .submissionUrl(domain.getSubmissionUrl())
                .submittedAt(domain.getSubmittedAt())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
