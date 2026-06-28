package com.workspace.application.port.in.workspace;

import java.util.UUID;
import com.workspace.domain.model.workspace.WorkSpace;

public interface CreateWorkspaceUseCase {
    WorkSpace createWorkspace(Command command);

    record Command(
        String name,
        String slug,
        String description,
        UUID ownerId
    ) {}
}
