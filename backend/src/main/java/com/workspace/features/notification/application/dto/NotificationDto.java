package com.workspace.features.notification.application.dto;

import com.workspace.features.notification.application.dto.*;
import com.workspace.features.notification.application.service.*;
import com.workspace.features.notification.infrastructure.persistence.entity.*;
import com.workspace.features.notification.infrastructure.persistence.jpaRepo.*;

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
public class NotificationDto {
    private UUID id;
    private String title;
    private String content;
    private boolean isRead;
    private String type;
    private String link;
    private OffsetDateTime createdAt;

    public static NotificationDto fromEntity(Notification notification) {
        if (notification == null) return null;
        return NotificationDto.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .isRead(notification.isRead())
                .type(notification.getType())
                .link(notification.getLink())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
