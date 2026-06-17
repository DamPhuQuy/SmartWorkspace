package com.workspace.features.workspace.infrastructure.web.dto;

import com.workspace.features.workspace.application.dto.*;
import com.workspace.features.workspace.application.service.*;
import com.workspace.features.workspace.infrastructure.persistence.entity.*;
import com.workspace.features.workspace.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.workspace.infrastructure.web.dto.*;

import lombok.Data;

@Data
public class InviteMemberRequest {
    private String email;
    private String roleName;
}
