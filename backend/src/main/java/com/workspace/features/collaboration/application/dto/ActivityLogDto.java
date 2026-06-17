package com.workspace.features.collaboration.application.dto;

import com.workspace.features.collaboration.application.dto.*;
import com.workspace.features.collaboration.application.service.*;
import com.workspace.features.collaboration.infrastructure.persistence.entity.*;
import com.workspace.features.collaboration.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.collaboration.infrastructure.web.dto.*;

import com.workspace.features.user.application.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityLogDto {
    private UUID id;
    private UUID workspaceId;
    private UserDto user;
    private String action;
    private String entityType;
    private UUID entityId;
    private String details;
    private OffsetDateTime createdAt;

    public static ActivityLogDto fromEntity(ActivityLog log) {
        if (log == null) return null;
        return ActivityLogDto.builder()
                .id(log.getId())
                .workspaceId(log.getWorkspace() != null ? log.getWorkspace().getId() : null)
                .user(UserDto.fromEntity(log.getUser()))
                .action(log.getAction())
                .entityType(log.getEntityType())
                .entityId(log.getEntityId())
                .details(log.getDetails())
                .createdAt(log.getCreatedAt())
                .build();
    }
}
