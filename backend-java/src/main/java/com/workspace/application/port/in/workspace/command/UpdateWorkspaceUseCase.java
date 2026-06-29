package com.workspace.application.port.in.workspace.command;

import java.util.UUID;
import com.workspace.domain.model.workspace.Workspace;

public interface UpdateWorkspaceUseCase {
    Workspace updateWorkspace(Command command);

    record Command(
        UUID id,
        String name,
        String slug,
        String description
    ) {}
}
