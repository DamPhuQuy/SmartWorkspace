package com.workspace.infrastructure.adapter.in.web.mapper;

import java.util.UUID;
import com.workspace.domain.model.role.Role;
import com.workspace.domain.model.workspace.WorkspaceMemberRole;
import com.workspace.infrastructure.adapter.in.web.dto.RoleDto;

public class RoleWebMapper {

    private RoleWebMapper() {}

    public static RoleDto.RoleResponse toResponse(Role role) {
        if (role == null) {
            return null;
        }

        UUID workspaceId = role.getWorkspace() != null ? role.getWorkspace().getId() : null;

        return new RoleDto.RoleResponse(
            role.getId(),
            workspaceId,
            role.getName(),
            role.getCreatedAt(),
            role.getUpdatedAt()
        );
    }

    public static RoleDto.WorkspaceMemberRoleResponse toMemberRoleResponse(WorkspaceMemberRole memberRole) {
        if (memberRole == null) {
            return null;
        }

        return new RoleDto.WorkspaceMemberRoleResponse(
            memberRole.getId(),
            memberRole.getWorkspaceMemberId(),
            memberRole.getRoleId()
        );
    }
}
