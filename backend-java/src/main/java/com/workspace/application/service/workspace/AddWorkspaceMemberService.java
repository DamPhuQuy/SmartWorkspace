package com.workspace.application.service.workspace;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.workspace.AddWorkspaceMemberUseCase;
import com.workspace.application.port.out.user.UserRepositoryPort;
import com.workspace.application.port.out.workspace.WorkSpaceMemberRepositoryPort;
import com.workspace.application.port.out.workspace.WorkSpaceRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.user.User;
import com.workspace.domain.model.workspace.WorkSpace;
import com.workspace.domain.model.workspace.WorkSpaceMember;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddWorkspaceMemberService implements AddWorkspaceMemberUseCase {

    private final WorkSpaceRepositoryPort workSpaceRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final WorkSpaceMemberRepositoryPort workSpaceMemberRepositoryPort;

    @Override
    @Transactional
    public WorkSpaceMember addMember(Command command) {
        WorkSpace workspace = workSpaceRepositoryPort.findById(command.workspaceId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace with ID " + command.workspaceId() + " not found"));

        User user = userRepositoryPort.findById(command.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + command.userId() + " not found"));

        if (workSpaceMemberRepositoryPort.existsByWorkspaceIdAndUserId(command.workspaceId(), command.userId())) {
            throw new DomainException("User is already a member of this workspace");
        }

        WorkSpaceMember member = WorkSpaceMember.builder()
                .id(UUID.randomUUID())
                .workspace(workspace)
                .user(user)
                .joinedAt(Instant.now())
                .build();

        return workSpaceMemberRepositoryPort.save(member);
    }
}
