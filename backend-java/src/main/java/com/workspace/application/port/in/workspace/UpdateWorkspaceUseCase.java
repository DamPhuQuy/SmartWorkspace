package com.workspace.application.port.in.workspace;

import java.util.UUID;
import com.workspace.domain.model.workspace.WorkSpace;

public interface UpdateWorkspaceUseCase {
    WorkSpace updateWorkspace(Command command);

    record Command(
        UUID id,
        String name,
        String slug,
        String description
    ) {}
}
