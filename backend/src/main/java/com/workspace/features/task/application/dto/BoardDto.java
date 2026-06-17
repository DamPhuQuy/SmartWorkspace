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
public class BoardDto {
    private UUID id;
    private UUID projectId;
    private String name;
    private List<BoardColumnDto> columns;

    public static BoardDto fromEntity(Board board, List<BoardColumnDto> columns) {
        if (board == null) return null;
        return BoardDto.builder()
                .id(board.getId())
                .projectId(board.getProject() != null ? board.getProject().getId() : null)
                .name(board.getName())
                .columns(columns)
                .build();
    }
}
