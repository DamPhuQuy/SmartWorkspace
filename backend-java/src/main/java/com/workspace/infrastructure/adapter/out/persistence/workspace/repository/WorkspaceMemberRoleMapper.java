package com.workspace.infrastructure.adapter.out.persistence.workspace.repository;

import com.workspace.domain.model.workspace.WorkspaceMemberRole;
import com.workspace.infrastructure.adapter.out.persistence.role.repository.RoleMapper;
import com.workspace.infrastructure.adapter.out.persistence.workspace.entity.WorkspaceMemberRoleEntity;


public class WorkspaceMemberRoleMapper {

    private WorkspaceMemberRoleMapper() {}

    public static WorkspaceMemberRole toDomain(WorkspaceMemberRoleEntity entity) {
        if (entity == null) {
            return null;
        }

        return WorkspaceMemberRole.builder()
                .id(entity.getId())
                .workspaceMember(WorkspaceMemberMapper.toDomain(entity.getWorkspaceMember()))
                .role(RoleMapper.toDomain(entity.getRole()))
                .build();
    }

    public static WorkspaceMemberRoleEntity toEntity(WorkspaceMemberRole domain) {
        if (domain == null) {
            return null;
        }

        return WorkspaceMemberRoleEntity.builder()
                .id(domain.getId())
                .workspaceMember(WorkspaceMemberMapper.toEntity(domain.getWorkspaceMember()))
                .role(RoleMapper.toEntity(domain.getRole()))
                .build();
    }
}
