package com.workspace.features.event.application.dto;

import com.workspace.features.event.application.dto.*;
import com.workspace.features.event.application.service.*;
import com.workspace.features.event.infrastructure.persistence.entity.*;
import com.workspace.features.event.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.event.infrastructure.web.dto.*;

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
public class EventDto {
    private UUID id;
    private UUID workspaceId;
    private String title;
    private String description;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private String location;
    private UserDto organizer;

    public static EventDto fromEntity(Event event) {
        if (event == null) return null;
        return EventDto.builder()
                .id(event.getId())
                .workspaceId(event.getWorkspace() != null ? event.getWorkspace().getId() : null)
                .title(event.getTitle())
                .description(event.getDescription())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .location(event.getLocation())
                .organizer(UserDto.fromEntity(event.getOrganizer()))
                .build();
    }
}
