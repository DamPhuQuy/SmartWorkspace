package com.workspace.infrastructure.adapter.out.persistence.workspace;

import com.workspace.domain.model.workspace.WorkspaceMemberRole;
import com.workspace.infrastructure.adapter.out.persistence.user.RoleMapper;

public class WorkspaceMemberRoleMapper {

    private WorkspaceMemberRoleMapper() {}

    public static WorkspaceMemberRole toDomain(WorkspaceMemberRoleEntity entity) {
        if (entity == null) {
            return null;
        }

        return WorkspaceMemberRole.builder()
                .id(entity.getId())
                .workspaceMember(WorkSpaceMemberMapper.toDomain(entity.getWorkspaceMember()))
                .role(RoleMapper.toDomain(entity.getRole()))
                .build();
    }

    public static WorkspaceMemberRoleEntity toEntity(WorkspaceMemberRole domain) {
        if (domain == null) {
            return null;
        }

        return WorkspaceMemberRoleEntity.builder()
                .id(domain.getId())
                .workspaceMember(WorkSpaceMemberMapper.toEntity(domain.getWorkspaceMember()))
                .role(RoleMapper.toEntity(domain.getRole()))
                .build();
    }
}
