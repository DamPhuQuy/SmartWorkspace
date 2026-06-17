package com.workspace.features.task.infrastructure.web.dto;

import com.workspace.features.task.application.dto.*;
import com.workspace.features.task.application.service.*;
import com.workspace.features.task.infrastructure.persistence.entity.*;
import com.workspace.features.task.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.task.infrastructure.web.dto.*;

import lombok.Data;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class CreateTaskRequest {
    private String title;
    private String description;
    private String priority;
    private OffsetDateTime dueDate;
    private Set<UUID> assigneeIds;
}
