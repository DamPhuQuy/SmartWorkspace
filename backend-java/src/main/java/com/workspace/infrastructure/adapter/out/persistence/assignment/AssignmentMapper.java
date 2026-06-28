package com.workspace.infrastructure.adapter.out.persistence.assignment;

import com.workspace.domain.model.assignment.Assignment;
import com.workspace.infrastructure.adapter.out.persistence.workspace.WorkSpaceMapper;
import com.workspace.infrastructure.adapter.out.persistence.workspace.WorkSpaceMemberMapper;

public class AssignmentMapper {

    private AssignmentMapper() {}

    public static Assignment toDomain(AssignmentEntity entity) {
        if (entity == null) {
            return null;
        }

        return Assignment.builder()
                .id(entity.getId())
                .workspace(WorkSpaceMapper.toDomain(entity.getWorkspace()))
                .title(entity.getTitle())
                .description(entity.getDescription())
                .deadline(entity.getDeadline())
                .createdBy(WorkSpaceMemberMapper.toDomain(entity.getCreatedBy()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static AssignmentEntity toEntity(Assignment domain) {
        if (domain == null) {
            return null;
        }

        return AssignmentEntity.builder()
                .id(domain.getId())
                .workspace(WorkSpaceMapper.toEntity(domain.getWorkspace()))
                .title(domain.getTitle())
                .description(domain.getDescription())
                .deadline(domain.getDeadline())
                .createdBy(WorkSpaceMemberMapper.toEntity(domain.getCreatedBy()))
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
