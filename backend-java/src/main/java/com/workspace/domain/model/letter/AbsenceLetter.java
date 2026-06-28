package com.workspace.domain.model.letter;

import java.time.LocalDate;
import java.util.UUID;
import com.workspace.domain.model.meeting.MeetingSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbsenceLetter {
    private UUID letterId;
    private Letter letter;
    private MeetingSchedule workspaceMeetingSchedule;
    private LocalDate absenceDate;
}
