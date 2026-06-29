package com.workspace.infrastructure.adapter.out.persistence.role.repository;

import com.workspace.domain.model.role.Role;
import com.workspace.infrastructure.adapter.out.persistence.role.entity.RoleEntity;
import com.workspace.infrastructure.adapter.out.persistence.workspace.repository.WorkspaceMapper;


public class RoleMapper {

    private RoleMapper() {}

    public static Role toDomain(RoleEntity entity) {
        if (entity == null) {
            return null;
        }

        return Role.builder()
                .id(entity.getId())
                .workspace(WorkspaceMapper.toDomain(entity.getWorkspace()))
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static RoleEntity toEntity(Role domain) {
        if (domain == null) {
            return null;
        }

        return RoleEntity.builder()
                .id(domain.getId())
                .workspace(WorkspaceMapper.toEntity(domain.getWorkspace()))
                .name(domain.getName())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
