package com.workspace.domain.model.team;

import java.time.Instant;
import java.util.UUID;
import com.workspace.domain.model.workspace.WorkSpaceMember;
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
    private WorkSpaceMember workspaceMember;
    private Instant joinedAt;
}
