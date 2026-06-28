package com.workspace.domain.model.team;

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
public class TeamMember {
    private UUID id;
    private Team team;
    private WorkspaceMember workspaceMember;
    private Instant joinedAt;
}
