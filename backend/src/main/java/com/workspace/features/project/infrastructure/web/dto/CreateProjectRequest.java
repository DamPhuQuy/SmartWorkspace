package com.workspace.features.project.infrastructure.web.dto;

import com.workspace.features.project.application.dto.*;
import com.workspace.features.project.application.service.*;
import com.workspace.features.project.infrastructure.persistence.entity.*;
import com.workspace.features.project.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.project.infrastructure.web.dto.*;

import lombok.Data;

@Data
public class CreateProjectRequest {
    private String name;
    private String description;
}
