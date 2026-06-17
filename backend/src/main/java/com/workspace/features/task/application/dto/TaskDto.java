package com.workspace.features.task.application.dto;

import com.workspace.features.task.application.dto.*;
import com.workspace.features.task.application.service.*;
import com.workspace.features.task.infrastructure.persistence.entity.*;
import com.workspace.features.task.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.task.infrastructure.web.dto.*;

import com.workspace.features.user.application.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private UUID id;
    private UUID projectId;
    private UUID columnId;
    private String title;
    private String description;
    private String priority;
    private OffsetDateTime dueDate;
    private UserDto createdBy;
    private Set<UserDto> assignees;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public static TaskDto fromEntity(Task task) {
        if (task == null) return null;
        return TaskDto.builder()
                .id(task.getId())
                .projectId(task.getProject() != null ? task.getProject().getId() : null)
                .columnId(task.getColumn() != null ? task.getColumn().getId() : null)
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .createdBy(UserDto.fromEntity(task.getCreatedBy()))
                .assignees(task.getAssignees() != null ? task.getAssignees().stream().map(UserDto::fromEntity).collect(Collectors.toSet()) : null)
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
