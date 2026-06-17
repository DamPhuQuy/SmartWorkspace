package com.workspace.features.event.infrastructure.web.dto;

import com.workspace.features.event.application.dto.*;
import com.workspace.features.event.application.service.*;
import com.workspace.features.event.infrastructure.persistence.entity.*;
import com.workspace.features.event.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.event.infrastructure.web.dto.*;

import lombok.Data;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class CreateEventRequest {
    private String title;
    private String description;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private String location;
    private List<UUID> attendeeIds;
}
