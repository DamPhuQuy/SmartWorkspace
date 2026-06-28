package com.workspace.adapter.out.persistence.user;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import com.workspace.adapter.out.persistence.workspace.WorkSpaceEntity;

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
    name = "roles",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_workspace_role_name",
            columnNames = {"workspace_id", "name"}
        )
    }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class RoleEntity {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workspace_id", nullable = false)
    private WorkSpaceEntity workspace;

    @Column(
        name = "name",
        nullable = false
    )
    private String name;

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    @CreationTimestamp
    private Instant createdAt;

    @Column(
        name = "updated_at",
        nullable = false,
        updatable = true
    )
    @UpdateTimestamp
    private Instant updatedAt;
}
