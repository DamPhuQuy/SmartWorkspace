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
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardColumnDto {
    private UUID id;
    private UUID boardId;
    private String name;
    private int position;
    private List<TaskDto> tasks;

    public static BoardColumnDto fromEntity(BoardColumn col, List<TaskDto> tasks) {
        if (col == null) return null;
        return BoardColumnDto.builder()
                .id(col.getId())
                .boardId(col.getBoard() != null ? col.getBoard().getId() : null)
                .name(col.getName())
                .position(col.getPosition())
                .tasks(tasks)
                .build();
    }
}
