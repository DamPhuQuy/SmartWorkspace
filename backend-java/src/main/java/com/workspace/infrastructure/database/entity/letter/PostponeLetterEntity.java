package com.workspace.infrastructure.database.entity.letter;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.workspace.infrastructure.database.entity.assignment.AssignmentEntity;

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
    name = "workspace_member_assignment_postpone_letters",
    indexes = {
        @Index(
            name = "idx_postpone_letter_assignment_deadlines",
            columnList = "assignment_id, old_deadline, requested_deadline"
        )
    }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PostponeLetterEntity {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID letterId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "letter_id")
    private LetterEntity letter;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assignment_id", nullable = false)
    private AssignmentEntity assignment;

    @Column(name = "assignment_snapshot")
    private String assignmentSnapshot;

    @Column(name = "old_deadline", nullable = false)
    private Instant oldDeadline;

    @Column(name = "requested_deadline", nullable = false)
    private Instant requestedDeadline;
}
