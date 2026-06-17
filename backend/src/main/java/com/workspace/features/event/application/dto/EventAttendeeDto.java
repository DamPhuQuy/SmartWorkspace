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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventAttendeeDto {
    private UserDto user;
    private String status;

    public static EventAttendeeDto fromEntity(EventAttendee att) {
        if (att == null) return null;
        return EventAttendeeDto.builder()
                .user(UserDto.fromEntity(att.getUser()))
                .status(att.getStatus())
                .build();
    }
}
