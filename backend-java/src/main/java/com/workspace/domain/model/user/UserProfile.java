package com.workspace.domain.model.user;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String avatarUrl;
    private String bio;
    private String discordId;
}
