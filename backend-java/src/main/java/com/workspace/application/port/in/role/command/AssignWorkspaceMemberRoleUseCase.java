package com.workspace.application.port.in.role.command;

import java.util.UUID;
import com.workspace.domain.model.workspace.WorkspaceMemberRole;

public interface AssignWorkspaceMemberRoleUseCase {
    WorkspaceMemberRole assignRole(Command command);

    record Command(
        UUID workspaceMemberId,
        UUID roleId
    ) {}
}
