package com.workspace.adapter.out.persistence.meeting;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingScheduleJpaRepository extends JpaRepository<MeetingScheduleEntity, UUID> {
    Optional<MeetingScheduleEntity> findByWorkspaceIdAndTitle(UUID workspaceId, String title);
    List<MeetingScheduleEntity> findByWorkspaceId(UUID workspaceId);
    boolean existsByWorkspaceIdAndTitle(UUID workspaceId, String title);
}
