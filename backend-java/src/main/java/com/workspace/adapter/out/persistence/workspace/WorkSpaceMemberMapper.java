package com.workspace.adapter.out.persistence.workspace;

import com.workspace.domain.model.workspace.WorkSpaceMember;
import com.workspace.adapter.out.persistence.user.UserMapper;

public class WorkSpaceMemberMapper {

    private WorkSpaceMemberMapper() {}

    public static WorkSpaceMember toDomain(WorkSpaceMemberEntity entity) {
        if (entity == null) {
            return null;
        }

        return WorkSpaceMember.builder()
                .id(entity.getId())
                .workspace(WorkSpaceMapper.toDomain(entity.getWorkspace()))
                .user(UserMapper.toDomain(entity.getUser()))
                .joinedAt(entity.getJoinedAt())
                .build();
    }

    public static WorkSpaceMemberEntity toEntity(WorkSpaceMember domain) {
        if (domain == null) {
            return null;
        }

        return WorkSpaceMemberEntity.builder()
                .id(domain.getId())
                .workspace(WorkSpaceMapper.toEntity(domain.getWorkspace()))
                .user(UserMapper.toEntity(domain.getUser()))
                .joinedAt(domain.getJoinedAt())
                .build();
    }
}
