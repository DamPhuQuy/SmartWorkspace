package com.workspace.infrastructure.database.entity.letter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.workspace.infrastructure.database.entity.meeting.MeetingScheduleEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "workspace_member_late_letters",
    indexes = {
        @Index(
            name = "idx_workspace_member_late_letter_schedule_date",
            columnList = "workspace_meeting_schedule_id, late_date"
        )
    }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class LateLetterEntity {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID letterId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "letter_id")
    private LetterEntity letter;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workspace_meeting_schedule_id", nullable = false)
    private MeetingScheduleEntity workspaceMeetingSchedule;

    @Column(name = "late_date", nullable = false)
    private LocalDate lateDate;

    @Column(name = "expected_arrival_time", nullable = false)
    private Instant expectedArrivalTime;
}
