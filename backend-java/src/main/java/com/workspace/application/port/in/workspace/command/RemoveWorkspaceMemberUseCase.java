package com.workspace.application.port.in.workspace.command;

import java.util.UUID;

public interface RemoveWorkspaceMemberUseCase {
    void removeMember(Command command);

    record Command(
        UUID workspaceId,
        UUID userId
    ) {}
}
