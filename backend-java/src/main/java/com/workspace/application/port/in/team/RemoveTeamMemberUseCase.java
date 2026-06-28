package com.workspace.application.port.in.team;

import java.util.UUID;

public interface RemoveTeamMemberUseCase {
    void removeTeamMember(Command command);

    record Command(
        UUID teamId,
        UUID workspaceMemberId
    ) {}
}
