package com.workspace.adapter.out.persistence.user;

import com.workspace.domain.model.user.Role;
import com.workspace.adapter.out.persistence.workspace.WorkSpaceMapper;

public class RoleMapper {

    private RoleMapper() {}

    public static Role toDomain(RoleEntity entity) {
        if (entity == null) {
            return null;
        }

        return Role.builder()
                .id(entity.getId())
                .workspace(WorkSpaceMapper.toDomain(entity.getWorkspace()))
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
                .workspace(WorkSpaceMapper.toEntity(domain.getWorkspace()))
                .name(domain.getName())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
