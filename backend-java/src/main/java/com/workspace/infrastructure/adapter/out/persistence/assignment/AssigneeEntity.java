package com.workspace.infrastructure.adapter.out.persistence.assignment;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import com.workspace.infrastructure.adapter.out.persistence.workspace.WorkSpaceMemberEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "assignment_assignees",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_assignment_assignee",
            columnNames = {"assignment_id", "workspace_member_id"}
        )
    }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class AssigneeEntity {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assignment_id", nullable = false)
    private AssignmentEntity assignment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workspace_member_id", nullable = false)
    private WorkSpaceMemberEntity workspaceMember;

    @Column(name = "assigned_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant assignedAt;
}
