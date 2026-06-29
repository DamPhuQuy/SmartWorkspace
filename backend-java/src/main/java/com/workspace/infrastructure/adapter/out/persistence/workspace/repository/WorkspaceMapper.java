package com.workspace.infrastructure.adapter.out.persistence.workspace.repository;

import com.workspace.domain.model.workspace.Workspace;
import com.workspace.infrastructure.adapter.out.persistence.user.repository.UserMapper;
import com.workspace.infrastructure.adapter.out.persistence.workspace.entity.WorkspaceEntity;


public class WorkspaceMapper {

    private WorkspaceMapper() {}

    public static Workspace toDomain(WorkspaceEntity entity) {
        if (entity == null) {
            return null;
        }

        return Workspace.builder()
                .id(entity.getId())
                .name(entity.getName())
                .slug(entity.getSlug())
                .description(entity.getDescription())
                .owner(UserMapper.toDomain(entity.getOwner()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static WorkspaceEntity toEntity(Workspace domain) {
        if (domain == null) {
            return null;
        }

        return WorkspaceEntity.builder()
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
