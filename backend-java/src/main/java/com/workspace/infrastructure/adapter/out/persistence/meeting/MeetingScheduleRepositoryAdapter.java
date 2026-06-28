package com.workspace.infrastructure.adapter.out.persistence.meeting;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

import com.workspace.application.port.out.meeting.MeetingScheduleRepositoryPort;
import com.workspace.domain.model.meeting.MeetingSchedule;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MeetingScheduleRepositoryAdapter implements MeetingScheduleRepositoryPort {

    private final MeetingScheduleJpaRepository meetingScheduleJpaRepository;

    @Override
    public Optional<MeetingSchedule> findById(UUID id) {
        return meetingScheduleJpaRepository.findById(id)
                .map(MeetingScheduleMapper::toDomain);
    }

    @Override
    public Optional<MeetingSchedule> findByWorkspaceIdAndTitle(UUID workspaceId, String title) {
        return meetingScheduleJpaRepository.findByWorkspaceIdAndTitle(workspaceId, title)
                .map(MeetingScheduleMapper::toDomain);
    }

    @Override
    public List<MeetingSchedule> findByWorkspaceId(UUID workspaceId) {
        return meetingScheduleJpaRepository.findByWorkspaceId(workspaceId)
                .stream()
                .map(MeetingScheduleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public MeetingSchedule save(MeetingSchedule meetingSchedule) {
        MeetingScheduleEntity entity = MeetingScheduleMapper.toEntity(meetingSchedule);
        MeetingScheduleEntity savedEntity = meetingScheduleJpaRepository.save(entity);
        return MeetingScheduleMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByWorkspaceIdAndTitle(UUID workspaceId, String title) {
        return meetingScheduleJpaRepository.existsByWorkspaceIdAndTitle(workspaceId, title);
    }

    @Override
    public void deleteById(UUID id) {
        meetingScheduleJpaRepository.deleteById(id);
    }
}
