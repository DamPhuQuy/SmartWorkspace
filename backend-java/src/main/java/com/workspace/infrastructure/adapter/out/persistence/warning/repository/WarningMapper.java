package com.workspace.infrastructure.adapter.out.persistence.warning.repository;

import com.workspace.domain.model.warning.Warning;
import com.workspace.infrastructure.adapter.out.persistence.warning.entity.WarningEntity;
import com.workspace.infrastructure.adapter.out.persistence.workspace.entity.WorkspaceMemberEntity;


public class WarningMapper {

    private WarningMapper() {}

    public static Warning toDomain(WarningEntity entity) {
        if (entity == null) {
            return null;
        }

        return Warning.builder()
                .id(entity.getId())
                .workspaceMemberId(entity.getWorkspaceMember() != null ? entity.getWorkspaceMember().getId() : null)
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
                .workspaceMember(domain.getWorkspaceMemberId() != null ? WorkspaceMemberEntity.builder().id(domain.getWorkspaceMemberId()).build() : null)
                .warningType(domain.getWarningType())
                .description(domain.getDescription())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
