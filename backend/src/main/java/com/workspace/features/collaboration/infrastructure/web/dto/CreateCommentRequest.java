package com.workspace.features.collaboration.infrastructure.web.dto;

import com.workspace.features.collaboration.application.dto.*;
import com.workspace.features.collaboration.application.service.*;
import com.workspace.features.collaboration.infrastructure.persistence.entity.*;
import com.workspace.features.collaboration.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.collaboration.infrastructure.web.dto.*;

import lombok.Data;

@Data
public class CreateCommentRequest {
    private String content;
}
