package com.workspace.features.user.application.dto;

import com.workspace.features.user.application.dto.*;
import com.workspace.features.user.infrastructure.persistence.entity.*;
import com.workspace.features.user.infrastructure.persistence.jpaRepo.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID id;
    private String email;
    private String fullName;
    private String avatarUrl;

    public static UserDto fromEntity(User user) {
        if (user == null) return null;
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }
}
