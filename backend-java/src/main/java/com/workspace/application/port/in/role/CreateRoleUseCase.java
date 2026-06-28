package com.workspace.application.port.in.role;

import java.util.UUID;
import com.workspace.domain.model.user.Role;

public interface CreateRoleUseCase {
    Role createRole(Command command);

    record Command(
        UUID workspaceId,
        String name
    ) {}
}
