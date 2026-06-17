package com.workspace.features.task.application.dto;

import com.workspace.features.task.application.dto.*;
import com.workspace.features.task.application.service.*;
import com.workspace.features.task.infrastructure.persistence.entity.*;
import com.workspace.features.task.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.task.infrastructure.web.dto.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubtaskDto {
    private UUID id;
    private UUID taskId;
    private String title;
    private boolean isCompleted;
    private int position;

    public static SubtaskDto fromEntity(Subtask subtask) {
        if (subtask == null) return null;
        return SubtaskDto.builder()
                .id(subtask.getId())
                .taskId(subtask.getTask() != null ? subtask.getTask().getId() : null)
                .title(subtask.getTitle())
                .isCompleted(subtask.isCompleted())
                .position(subtask.getPosition())
                .build();
    }
}
