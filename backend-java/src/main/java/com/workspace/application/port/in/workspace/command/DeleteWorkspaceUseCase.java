package com.workspace.application.port.in.workspace.command;

import java.util.UUID;

public interface DeleteWorkspaceUseCase {
    void deleteWorkspace(UUID id);
}
