package com.workspace.adapter.out.persistence.assignment;

import com.workspace.domain.model.assignment.Assignee;
import com.workspace.adapter.out.persistence.workspace.WorkSpaceMemberMapper;

public class AssigneeMapper {

    private AssigneeMapper() {}

    public static Assignee toDomain(AssigneeEntity entity) {
        if (entity == null) {
            return null;
        }

        return Assignee.builder()
                .id(entity.getId())
                .assignment(AssignmentMapper.toDomain(entity.getAssignment()))
                .workspaceMember(WorkSpaceMemberMapper.toDomain(entity.getWorkspaceMember()))
                .assignedAt(entity.getAssignedAt())
                .build();
    }

    public static AssigneeEntity toEntity(Assignee domain) {
        if (domain == null) {
            return null;
        }

        return AssigneeEntity.builder()
                .id(domain.getId())
                .assignment(AssignmentMapper.toEntity(domain.getAssignment()))
                .workspaceMember(WorkSpaceMemberMapper.toEntity(domain.getWorkspaceMember()))
                .assignedAt(domain.getAssignedAt())
                .build();
    }
}
