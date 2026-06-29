package com.workspace.infrastructure.adapter.out.persistence.warning.repository;

import com.workspace.domain.model.warning.Warning;
import com.workspace.infrastructure.adapter.out.persistence.warning.entity.WarningEntity;
import com.workspace.infrastructure.adapter.out.persistence.workspace.repository.WorkspaceMemberMapper;


public class WarningMapper {

    private WarningMapper() {}

    public static Warning toDomain(WarningEntity entity) {
        if (entity == null) {
            return null;
        }

        return Warning.builder()
                .id(entity.getId())
                .workspaceMember(WorkspaceMemberMapper.toDomain(entity.getWorkspaceMember()))
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
                .workspaceMember(WorkspaceMemberMapper.toEntity(domain.getWorkspaceMember()))
                .warningType(domain.getWarningType())
                .description(domain.getDescription())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
