package com.workspace.application.service.role.command;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.role.command.AssignWorkspaceMemberRoleUseCase;
import com.workspace.application.port.out.role.RoleRepositoryPort;
import com.workspace.application.port.out.workspace.WorkspaceRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.role.Role;
import com.workspace.domain.model.workspace.WorkspaceMember;
import com.workspace.domain.model.workspace.WorkspaceMemberRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignWorkspaceMemberRoleService implements AssignWorkspaceMemberRoleUseCase {

    private final RoleRepositoryPort roleRepositoryPort;
    private final WorkspaceRepositoryPort workspaceRepositoryPort;
    
    @Override
    @Transactional
    public WorkspaceMemberRole assignRole(Command command) {
        WorkspaceMember member = workspaceRepositoryPort.findMemberById(command.workspaceMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member with ID " + command.workspaceMemberId() + " not found"));

        Role role = roleRepositoryPort.findById(command.roleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role with ID " + command.roleId() + " not found"));

        // Ensure role belongs to the same workspace as the member
        if (!role.getWorkspace().getId().equals(member.getWorkspaceId())) {
            throw new DomainException("Role must belong to the same workspace as the member");
        }

        if (workspaceRepositoryPort.existsMemberRoleByWorkspaceMemberIdAndRoleId(command.workspaceMemberId(), command.roleId())) {
            throw new DomainException("Member already has this role assigned");
        }

        WorkspaceMemberRole memberRole = WorkspaceMemberRole.assign(member.getId(), role.getId());

        return workspaceRepositoryPort.saveMemberRole(memberRole);
    }
}
