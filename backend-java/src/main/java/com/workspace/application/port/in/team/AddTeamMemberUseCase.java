package com.workspace.application.port.in.team;

import java.util.UUID;
import com.workspace.domain.model.team.TeamMember;

public interface AddTeamMemberUseCase {
    TeamMember addTeamMember(Command command);

    record Command(
        UUID teamId,
        UUID workspaceMemberId
    ) {}
}
