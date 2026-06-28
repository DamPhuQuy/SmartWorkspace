package com.workspace.domain.model.meeting;

import java.time.Instant;
import java.util.UUID;
import com.workspace.domain.model.workspace.WorkSpace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingSchedule {
    private UUID id;
    private WorkSpace workspace;
    private String title;
    private Instant startTime;
    private Instant endTime;
    private Instant createdAt;
    private Instant updatedAt;
}
