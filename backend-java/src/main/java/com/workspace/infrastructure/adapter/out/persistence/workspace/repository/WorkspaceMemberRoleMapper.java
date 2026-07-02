package com.workspace.infrastructure.adapter.out.persistence.workspace.repository;

import com.workspace.domain.model.workspace.WorkspaceMemberRole;
import com.workspace.infrastructure.adapter.out.persistence.role.entity.RoleEntity;
import com.workspace.infrastructure.adapter.out.persistence.workspace.entity.WorkspaceMemberEntity;
import com.workspace.infrastructure.adapter.out.persistence.workspace.entity.WorkspaceMemberRoleEntity;


public class WorkspaceMemberRoleMapper {

    private WorkspaceMemberRoleMapper() {}

    public static WorkspaceMemberRole toDomain(WorkspaceMemberRoleEntity entity) {
        if (entity == null) {
            return null;
        }

        return WorkspaceMemberRole.builder()
                .id(entity.getId())
                .workspaceMemberId(entity.getWorkspaceMember() != null ? entity.getWorkspaceMember().getId() : null)
                .roleId(entity.getRole() != null ? entity.getRole().getId() : null)
                .build();
    }

    public static WorkspaceMemberRoleEntity toEntity(WorkspaceMemberRole domain) {
        if (domain == null) {
            return null;
        }

        return WorkspaceMemberRoleEntity.builder()
                .id(domain.getId())
                .workspaceMember(domain.getWorkspaceMemberId() != null ? WorkspaceMemberEntity.builder().id(domain.getWorkspaceMemberId()).build() : null)
                .role(domain.getRoleId() != null ? RoleEntity.builder().id(domain.getRoleId()).build() : null)
                .build();
    }
}
