package com.workspace.application.port.in.workspace;

import java.util.UUID;
import com.workspace.domain.model.workspace.WorkspaceMember;

public interface AddWorkspaceMemberUseCase {
    WorkspaceMember addMember(Command command);

    record Command(
        UUID workspaceId,
        UUID userId
    ) {}
}
