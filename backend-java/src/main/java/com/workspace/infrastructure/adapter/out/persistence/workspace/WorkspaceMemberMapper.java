package com.workspace.infrastructure.adapter.out.persistence.workspace;

import com.workspace.domain.model.workspace.WorkspaceMember;
import com.workspace.infrastructure.adapter.out.persistence.user.UserMapper;

public class WorkspaceMemberMapper {

    private WorkspaceMemberMapper() {}

    public static WorkspaceMember toDomain(WorkspaceMemberEntity entity) {
        if (entity == null) {
            return null;
        }

        return WorkspaceMember.builder()
                .id(entity.getId())
                .workspace(WorkspaceMapper.toDomain(entity.getWorkspace()))
                .user(UserMapper.toDomain(entity.getUser()))
                .joinedAt(entity.getJoinedAt())
                .build();
    }

    public static WorkspaceMemberEntity toEntity(WorkspaceMember domain) {
        if (domain == null) {
            return null;
        }

        return WorkspaceMemberEntity.builder()
                .id(domain.getId())
                .workspace(WorkspaceMapper.toEntity(domain.getWorkspace()))
                .user(UserMapper.toEntity(domain.getUser()))
                .joinedAt(domain.getJoinedAt())
                .build();
    }
}
