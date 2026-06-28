package com.workspace.application.service.letter;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.letter.SubmitLateLetterUseCase;
import com.workspace.application.port.out.letter.LateLetterRepositoryPort;
import com.workspace.application.port.out.letter.LetterRepositoryPort;
import com.workspace.application.port.out.meeting.MeetingScheduleRepositoryPort;
import com.workspace.application.port.out.workspace.WorkSpaceMemberRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.letter.LateLetter;
import com.workspace.domain.model.letter.Letter;
import com.workspace.domain.model.meeting.MeetingSchedule;
import com.workspace.domain.model.workspace.WorkSpaceMember;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubmitLateLetterService implements SubmitLateLetterUseCase {

    private final LetterRepositoryPort letterRepositoryPort;
    private final LateLetterRepositoryPort lateLetterRepositoryPort;
    private final WorkSpaceMemberRepositoryPort workSpaceMemberRepositoryPort;
    private final MeetingScheduleRepositoryPort meetingScheduleRepositoryPort;

    @Override
    @Transactional
    public LateLetter submitLateLetter(Command command) {
        WorkSpaceMember member = workSpaceMemberRepositoryPort.findById(command.workspaceMemberId())
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
                .letterType("late")
                .description(command.description())
                .status("pending")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Letter savedLetter = letterRepositoryPort.save(letter);

        LateLetter lateLetter = LateLetter.builder()
                .letterId(letterId)
                .letter(savedLetter)
                .workspaceMeetingSchedule(meeting)
                .lateDate(command.lateDate())
                .expectedArrivalTime(command.expectedArrivalTime())
                .build();

        return lateLetterRepositoryPort.save(lateLetter);
    }
}
