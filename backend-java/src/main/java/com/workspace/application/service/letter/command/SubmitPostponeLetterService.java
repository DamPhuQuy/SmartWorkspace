package com.workspace.application.service.letter.command;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.letter.command.SubmitPostponeLetterUseCase;
import com.workspace.application.port.out.assignment.AssignmentRepositoryPort;
import com.workspace.application.port.out.letter.LetterRepositoryPort;
import com.workspace.application.port.out.workspace.WorkspaceRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.assignment.Assignment;
import com.workspace.domain.model.letter.Letter;
import com.workspace.domain.model.letter.PostponeLetter;
import com.workspace.domain.model.workspace.WorkspaceMember;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubmitPostponeLetterService implements SubmitPostponeLetterUseCase {

    private final LetterRepositoryPort letterRepositoryPort;
    private final WorkspaceRepositoryPort workspaceRepositoryPort;
    private final AssignmentRepositoryPort assignmentRepositoryPort;

    @Override
    @Transactional
    public PostponeLetter submitPostponeLetter(Command command) {
        WorkspaceMember member = workspaceRepositoryPort.findMemberById(command.workspaceMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member with ID " + command.workspaceMemberId() + " not found"));

        Assignment assignment = assignmentRepositoryPort.findById(command.assignmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Assignment with ID " + command.assignmentId() + " not found"));

        if (!member.getWorkspaceId().equals(assignment.getWorkspace().getId())) {
            throw new DomainException("Workspace member and assignment must belong to the same workspace");
        }

        if (command.requestedDeadline().isBefore(command.oldDeadline())) {
            throw new DomainException("Requested deadline must be after the old deadline");
        }

        UUID letterId = UUID.randomUUID();

        Letter letter = Letter.builder()
                .id(letterId)
                .workspaceMember(member)
                .letterType("postpone")
                .description(command.description())
                .status("pending")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Letter savedLetter = letterRepositoryPort.save(letter);

        PostponeLetter postponeLetter = PostponeLetter.builder()
                .letterId(letterId)
                .letter(savedLetter)
                .assignment(assignment)
                .assignmentSnapshot(command.assignmentSnapshot())
                .oldDeadline(command.oldDeadline())
                .requestedDeadline(command.requestedDeadline())
                .build();

        return letterRepositoryPort.savePostpone(postponeLetter);
    }
}
