package com.workspace.application.port.in.team.command;

import java.util.UUID;

public interface RemoveTeamMemberUseCase {
    void removeTeamMember(Command command);

    record Command(
        UUID teamId,
        UUID workspaceMemberId
    ) {}
}
