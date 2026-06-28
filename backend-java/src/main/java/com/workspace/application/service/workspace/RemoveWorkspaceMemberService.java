package com.workspace.application.service.workspace;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.workspace.RemoveWorkspaceMemberUseCase;
import com.workspace.application.port.out.workspace.WorkspaceRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.workspace.Workspace;
import com.workspace.domain.model.workspace.WorkspaceMember;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RemoveWorkspaceMemberService implements RemoveWorkspaceMemberUseCase {

    private final WorkspaceRepositoryPort workspaceRepositoryPort;
    
    @Override
    @Transactional
    public void removeMember(Command command) {
        Workspace workspace = workspaceRepositoryPort.findById(command.workspaceId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace with ID " + command.workspaceId() + " not found"));

        WorkspaceMember member = workspaceRepositoryPort.findMemberByWorkspaceIdAndUserId(command.workspaceId(), command.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member with User ID " + command.userId() + " not found in workspace"));

        // Owners cannot be removed from their own workspace
        if (workspace.getOwner().getId().equals(command.userId())) {
            throw new DomainException("The workspace owner cannot be removed from the workspace");
        }

        workspaceRepositoryPort.deleteMemberById(member.getId());
    }
}
