package com.workspace.application.service.workspace;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.workspace.DeleteWorkspaceUseCase;
import com.workspace.application.port.out.workspace.WorkspaceRepositoryPort;
import com.workspace.domain.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteWorkspaceService implements DeleteWorkspaceUseCase {

    private final WorkspaceRepositoryPort workspaceRepositoryPort;

    @Override
    @Transactional
    public void deleteWorkspace(UUID id) {
        if (!workspaceRepositoryPort.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Workspace with ID " + id + " not found");
        }
        workspaceRepositoryPort.deleteById(id);
    }
}
