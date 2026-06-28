package com.workspace.application.service.warning;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.warning.IssueWarningUseCase;
import com.workspace.application.port.out.warning.WarningRepositoryPort;
import com.workspace.application.port.out.workspace.WorkspaceRepositoryPort;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.workspace.WorkspaceMember;
import com.workspace.domain.model.warning.Warning;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueWarningService implements IssueWarningUseCase {

    private final WarningRepositoryPort warningRepositoryPort;
    private final WorkspaceRepositoryPort workspaceRepositoryPort;

    @Override
    @Transactional
    public Warning issueWarning(Command command) {
        WorkspaceMember member = workspaceRepositoryPort.findMemberById(command.workspaceMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member with ID " + command.workspaceMemberId() + " not found"));

        Warning warning = Warning.builder()
                .id(UUID.randomUUID())
                .workspaceMember(member)
                .warningType(command.warningType())
                .description(command.description())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return warningRepositoryPort.save(warning);
    }
}
