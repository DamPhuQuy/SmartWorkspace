package com.workspace.application.service.letter.command;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.letter.command.SubmitAbsenceLetterUseCase;
import com.workspace.application.port.out.letter.LetterRepositoryPort;
import com.workspace.application.port.out.meeting.MeetingScheduleRepositoryPort;
import com.workspace.application.port.out.workspace.WorkspaceRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.letter.AbsenceLetter;
import com.workspace.domain.model.letter.Letter;
import com.workspace.domain.model.meeting.MeetingSchedule;
import com.workspace.domain.model.workspace.WorkspaceMember;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubmitAbsenceLetterService implements SubmitAbsenceLetterUseCase {

    private final LetterRepositoryPort letterRepositoryPort;
    private final WorkspaceRepositoryPort workspaceRepositoryPort;
            private final MeetingScheduleRepositoryPort meetingScheduleRepositoryPort;

    @Override
    @Transactional
    public AbsenceLetter submitAbsenceLetter(Command command) {
        WorkspaceMember member = workspaceRepositoryPort.findMemberById(command.workspaceMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member with ID " + command.workspaceMemberId() + " not found"));

        MeetingSchedule meeting = meetingScheduleRepositoryPort.findById(command.meetingScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Meeting schedule with ID " + command.meetingScheduleId() + " not found"));

        if (!member.getWorkspace().getId().equals(meeting.getWorkspace().getId())) {
            throw new DomainException("Workspace member and meeting schedule must belong to the same workspace");
        }

        UUID letterId = UUID.randomUUID();

        Letter letter = Letter.builder()
                .id(letterId)
                .workspaceMember(member)
                .letterType("absence")
                .description(command.description())
                .status("pending")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Letter savedLetter = letterRepositoryPort.save(letter);

        AbsenceLetter absenceLetter = AbsenceLetter.builder()
                .letterId(letterId)
                .letter(savedLetter)
                .workspaceMeetingSchedule(meeting)
                .absenceDate(command.absenceDate())
                .build();

        return letterRepositoryPort.saveAbsence(absenceLetter);
    }
}
