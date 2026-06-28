package com.workspace.application.port.in.workspace;

import java.util.UUID;
import com.workspace.domain.model.workspace.WorkSpaceMember;

public interface AddWorkspaceMemberUseCase {
    WorkSpaceMember addMember(Command command);

    record Command(
        UUID workspaceId,
        UUID userId
    ) {}
}
