package com.workspace.infrastructure.adapter.out.persistence.warning;

import com.workspace.domain.model.warning.Warning;
import com.workspace.infrastructure.adapter.out.persistence.workspace.WorkSpaceMemberMapper;

public class WarningMapper {

    private WarningMapper() {}

    public static Warning toDomain(WarningEntity entity) {
        if (entity == null) {
            return null;
        }

        return Warning.builder()
                .id(entity.getId())
                .workspaceMember(WorkSpaceMemberMapper.toDomain(entity.getWorkspaceMember()))
                .warningType(entity.getWarningType())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static WarningEntity toEntity(Warning domain) {
        if (domain == null) {
            return null;
        }

        return WarningEntity.builder()
                .id(domain.getId())
                .workspaceMember(WorkSpaceMemberMapper.toEntity(domain.getWorkspaceMember()))
                .warningType(domain.getWarningType())
                .description(domain.getDescription())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
