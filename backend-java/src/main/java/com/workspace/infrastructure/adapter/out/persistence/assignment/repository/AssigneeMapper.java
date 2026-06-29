package com.workspace.infrastructure.adapter.out.persistence.assignment.repository;

import com.workspace.infrastructure.adapter.out.persistence.assignment.entity.AssigneeEntity;
import com.workspace.infrastructure.adapter.out.persistence.assignment.repository.AssignmentMapper;
import com.workspace.infrastructure.adapter.out.persistence.workspace.repository.WorkspaceMemberMapper;

import com.workspace.domain.model.assignment.Assignee;


public class AssigneeMapper {

    private AssigneeMapper() {}

    public static Assignee toDomain(AssigneeEntity entity) {
        if (entity == null) {
            return null;
        }

        return Assignee.builder()
                .id(entity.getId())
                .assignment(AssignmentMapper.toDomain(entity.getAssignment()))
                .workspaceMember(WorkspaceMemberMapper.toDomain(entity.getWorkspaceMember()))
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
                .workspaceMember(WorkspaceMemberMapper.toEntity(domain.getWorkspaceMember()))
                .assignedAt(domain.getAssignedAt())
                .build();
    }
}
