package com.workspace.application.port.in.team;

import java.util.UUID;
import com.workspace.domain.model.team.Team;

public interface CreateTeamUseCase {
    Team createTeam(Command command);

    record Command(
        UUID workspaceId,
        String name
    ) {}
}
