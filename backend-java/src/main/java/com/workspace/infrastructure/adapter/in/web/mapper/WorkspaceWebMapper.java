package com.workspace.infrastructure.adapter.in.web.mapper;

import java.util.UUID;
import com.workspace.domain.model.workspace.Workspace;
import com.workspace.domain.model.workspace.WorkspaceMember;
import com.workspace.infrastructure.adapter.in.web.dto.WorkspaceDto;

public class WorkspaceWebMapper {

    private WorkspaceWebMapper() {}

    public static WorkspaceDto.WorkspaceResponse toResponse(Workspace workspace) {
        if (workspace == null) {
            return null;
        }

        return new WorkspaceDto.WorkspaceResponse(
            workspace.getId(),
            workspace.getName(),
            workspace.getSlug(),
            workspace.getDescription(),
            UserWebMapper.toResponse(workspace.getOwner()),
            workspace.getCreatedAt(),
            workspace.getUpdatedAt()
        );
    }

    public static WorkspaceDto.WorkspaceMemberResponse toMemberResponse(WorkspaceMember member) {
        if (member == null) {
            return null;
        }

        UUID workspaceId = member.getWorkspace() != null ? member.getWorkspace().getId() : null;

        return new WorkspaceDto.WorkspaceMemberResponse(
            member.getId(),
            workspaceId,
            UserWebMapper.toResponse(member.getUser()),
            member.getJoinedAt()
        );
    }
}
