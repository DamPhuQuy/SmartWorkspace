package com.workspace.infrastructure.adapter.out.persistence.user;

import com.workspace.domain.model.user.Permission;

public class PermissionMapper {

    private PermissionMapper() {}

    public static Permission toDomain(PermissionEntity entity) {
        if (entity == null) {
            return null;
        }

        return Permission.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static PermissionEntity toEntity(Permission domain) {
        if (domain == null) {
            return null;
        }

        return PermissionEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }
}
