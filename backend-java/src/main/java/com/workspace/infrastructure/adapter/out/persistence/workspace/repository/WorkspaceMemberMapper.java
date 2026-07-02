package com.workspace.infrastructure.adapter.out.persistence.workspace.repository;

import com.workspace.domain.model.workspace.WorkspaceMember;
import com.workspace.infrastructure.adapter.out.persistence.user.entity.UserEntity;
import com.workspace.infrastructure.adapter.out.persistence.workspace.entity.WorkspaceEntity;
import com.workspace.infrastructure.adapter.out.persistence.workspace.entity.WorkspaceMemberEntity;


public class WorkspaceMemberMapper {

    private WorkspaceMemberMapper() {}

    public static WorkspaceMember toDomain(WorkspaceMemberEntity entity) {
        if (entity == null) {
            return null;
        }

        return WorkspaceMember.builder()
                .id(entity.getId())
                .workspaceId(entity.getWorkspace() != null ? entity.getWorkspace().getId() : null)
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .joinedAt(entity.getJoinedAt())
                .build();
    }

    public static WorkspaceMemberEntity toEntity(WorkspaceMember domain) {
        if (domain == null) {
            return null;
        }

        return WorkspaceMemberEntity.builder()
                .id(domain.getId())
                .workspace(domain.getWorkspaceId() != null ? WorkspaceEntity.builder().id(domain.getWorkspaceId()).build() : null)
                .user(domain.getUserId() != null ? UserEntity.builder().id(domain.getUserId()).build() : null)
                .joinedAt(domain.getJoinedAt())
                .build();
    }
}
