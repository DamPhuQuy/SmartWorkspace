package com.workspace.infrastructure.adapter.out.persistence.workspace;

import com.workspace.domain.model.workspace.WorkSpace;
import com.workspace.infrastructure.adapter.out.persistence.user.UserMapper;

public class WorkSpaceMapper {

    private WorkSpaceMapper() {}

    public static WorkSpace toDomain(WorkSpaceEntity entity) {
        if (entity == null) {
            return null;
        }

        return WorkSpace.builder()
                .id(entity.getId())
                .name(entity.getName())
                .slug(entity.getSlug())
                .description(entity.getDescription())
                .owner(UserMapper.toDomain(entity.getOwner()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static WorkSpaceEntity toEntity(WorkSpace domain) {
        if (domain == null) {
            return null;
        }

        return WorkSpaceEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .slug(domain.getSlug())
                .description(domain.getDescription())
                .owner(UserMapper.toEntity(domain.getOwner()))
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
