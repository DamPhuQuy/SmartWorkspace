package com.workspace.domain.model.workspace;

import java.time.Instant;
import java.util.UUID;
import com.workspace.domain.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Workspace {
    private UUID id;
    private String name;
    private String slug;
    private String description;
    private User owner;
    private Instant createdAt;
    private Instant updatedAt;
}
