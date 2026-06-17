package com.workspace.features.workspace.application.dto;

import com.workspace.features.workspace.application.dto.*;
import com.workspace.features.workspace.application.service.*;
import com.workspace.features.workspace.infrastructure.persistence.entity.*;
import com.workspace.features.workspace.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.workspace.infrastructure.web.dto.*;

import com.workspace.features.user.application.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceMemberDto {
    private UUID id;
    private UserDto user;
    private String roleName;

    public static WorkspaceMemberDto fromEntity(WorkspaceMember member) {
        if (member == null) return null;
        return WorkspaceMemberDto.builder()
                .id(member.getId())
                .user(UserDto.fromEntity(member.getUser()))
                .roleName(member.getRole() != null ? member.getRole().getName() : null)
                .build();
    }
}
