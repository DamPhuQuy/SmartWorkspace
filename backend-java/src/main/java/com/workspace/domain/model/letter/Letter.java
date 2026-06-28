package com.workspace.domain.model.letter;

import java.time.Instant;
import java.util.UUID;
import com.workspace.domain.model.workspace.WorkspaceMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Letter {
    private UUID id;
    private WorkspaceMember workspaceMember;
    private String letterType;
    private String description;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
}
