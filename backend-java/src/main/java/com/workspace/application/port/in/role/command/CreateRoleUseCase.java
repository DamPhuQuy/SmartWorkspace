package com.workspace.application.port.in.role.command;

import java.util.UUID;
import com.workspace.domain.model.role.Role;

public interface CreateRoleUseCase {
    Role createRole(Command command);

    record Command(
        UUID workspaceId,
        String name
    ) {}
}
