package com.workspace.features.auth.infrastructure.web.dto;

import com.workspace.features.auth.application.dto.*;
import com.workspace.features.auth.application.service.*;
import com.workspace.features.auth.infrastructure.config.*;
import com.workspace.features.auth.infrastructure.persistence.entity.*;
import com.workspace.features.auth.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.auth.infrastructure.web.dto.*;

import lombok.Data;

@Data
public class LogoutRequest {
    private String refreshToken;
}
