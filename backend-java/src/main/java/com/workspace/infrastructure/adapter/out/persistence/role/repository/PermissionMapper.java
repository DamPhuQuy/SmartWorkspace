package com.workspace.infrastructure.adapter.out.persistence.role.repository;

import com.workspace.domain.model.role.Permission;
import com.workspace.infrastructure.adapter.out.persistence.role.entity.PermissionEntity;

public class PermissionMapper {

    private PermissionMapper() {}

    public static Permission toDomain(PermissionEntity entity) {
        if (entity == null) {
            return null;
        }

        return Permission.builder()
                .id(entity.getId())
                .name(entity.getCode())
                .build();
    }

    public static PermissionEntity toEntity(Permission domain) {
        if (domain == null) {
            return null;
        }

        return PermissionEntity.builder()
            .id(domain.getId())
            .code(domain.getName())
            .build();
    }
}
