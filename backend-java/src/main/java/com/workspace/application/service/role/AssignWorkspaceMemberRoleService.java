package com.workspace.application.service.role;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.role.AssignWorkspaceMemberRoleUseCase;
import com.workspace.application.port.out.user.RoleRepositoryPort;
import com.workspace.application.port.out.workspace.WorkSpaceMemberRepositoryPort;
import com.workspace.application.port.out.workspace.WorkspaceMemberRoleRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.user.Role;
import com.workspace.domain.model.workspace.WorkSpaceMember;
import com.workspace.domain.model.workspace.WorkspaceMemberRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignWorkspaceMemberRoleService implements AssignWorkspaceMemberRoleUseCase {

    private final WorkSpaceMemberRepositoryPort workSpaceMemberRepositoryPort;
    private final RoleRepositoryPort roleRepositoryPort;
    private final WorkspaceMemberRoleRepositoryPort workspaceMemberRoleRepositoryPort;

    @Override
    @Transactional
    public WorkspaceMemberRole assignRole(Command command) {
        WorkSpaceMember member = workSpaceMemberRepositoryPort.findById(command.workspaceMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member with ID " + command.workspaceMemberId() + " not found"));

        Role role = roleRepositoryPort.findById(command.roleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role with ID " + command.roleId() + " not found"));

        // Ensure role belongs to the same workspace as the member
        if (!role.getWorkspace().getId().equals(member.getWorkspace().getId())) {
            throw new DomainException("Role must belong to the same workspace as the member");
        }

        if (workspaceMemberRoleRepositoryPort.existsByWorkspaceMemberIdAndRoleId(command.workspaceMemberId(), command.roleId())) {
            throw new DomainException("Member already has this role assigned");
        }

        WorkspaceMemberRole memberRole = WorkspaceMemberRole.builder()
                .id(UUID.randomUUID())
                .workspaceMember(member)
                .role(role)
                .build();

        return workspaceMemberRoleRepositoryPort.save(memberRole);
    }
}
