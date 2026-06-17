package com.workspace.features.project.application.dto;

import com.workspace.features.project.application.dto.*;
import com.workspace.features.project.application.service.*;
import com.workspace.features.project.infrastructure.persistence.entity.*;
import com.workspace.features.project.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.project.infrastructure.web.dto.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    private UUID id;
    private UUID workspaceId;
    private String name;
    private String description;
    private String status;

    public static ProjectDto fromEntity(Project project) {
        if (project == null) return null;
        return ProjectDto.builder()
                .id(project.getId())
                .workspaceId(project.getWorkspace() != null ? project.getWorkspace().getId() : null)
                .name(project.getName())
                .description(project.getDescription())
                .status(project.getStatus())
                .build();
    }
}
