package com.workspace.infrastructure.adapter.out.persistence.warning.entity;

import java.time.Instant;
import java.util.UUID;

import com.workspace.domain.model.warning.WarningType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import com.workspace.infrastructure.adapter.out.persistence.workspace.entity.WorkspaceMemberEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "workspace_member_warnings",
    indexes = {
        @Index(
            name = "idx_workspace_member_warning_type",
            columnList = "workspace_member_id, warning_type"
        )
    }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class WarningEntity {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workspace_member_id", nullable = false)
    private WorkspaceMemberEntity workspaceMember;

    @Column(name = "warning_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private WarningType warningType;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Instant updatedAt;
}
