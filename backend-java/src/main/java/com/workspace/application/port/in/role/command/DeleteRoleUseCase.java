package com.workspace.application.port.in.role.command;

import java.util.UUID;

public interface DeleteRoleUseCase {
    void deleteRole(UUID id);
}
