package com.workspace.features.task.infrastructure.web.dto;

import com.workspace.features.task.application.dto.*;
import com.workspace.features.task.application.service.*;
import com.workspace.features.task.infrastructure.persistence.entity.*;
import com.workspace.features.task.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.task.infrastructure.web.dto.*;

import lombok.Data;

@Data
public class CreateSubtaskRequest {
    private String title;
}
