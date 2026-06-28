package com.workspace.application.service.workspace;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.workspace.RemoveWorkspaceMemberUseCase;
import com.workspace.application.port.out.workspace.WorkSpaceMemberRepositoryPort;
import com.workspace.application.port.out.workspace.WorkSpaceRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.workspace.WorkSpace;
import com.workspace.domain.model.workspace.WorkSpaceMember;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RemoveWorkspaceMemberService implements RemoveWorkspaceMemberUseCase {

    private final WorkSpaceRepositoryPort workSpaceRepositoryPort;
    private final WorkSpaceMemberRepositoryPort workSpaceMemberRepositoryPort;

    @Override
    @Transactional
    public void removeMember(Command command) {
        WorkSpace workspace = workSpaceRepositoryPort.findById(command.workspaceId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace with ID " + command.workspaceId() + " not found"));

        WorkSpaceMember member = workSpaceMemberRepositoryPort.findByWorkspaceIdAndUserId(command.workspaceId(), command.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member with User ID " + command.userId() + " not found in workspace"));

        // Owners cannot be removed from their own workspace
        if (workspace.getOwner().getId().equals(command.userId())) {
            throw new DomainException("The workspace owner cannot be removed from the workspace");
        }

        workSpaceMemberRepositoryPort.deleteById(member.getId());
    }
}
