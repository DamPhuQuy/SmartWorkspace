package com.workspace.features.collaboration.infrastructure.persistence.entity;

import com.workspace.features.collaboration.application.dto.*;
import com.workspace.features.collaboration.application.service.*;
import com.workspace.features.collaboration.infrastructure.persistence.entity.*;
import com.workspace.features.collaboration.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.collaboration.infrastructure.web.dto.*;

import com.workspace.features.task.infrastructure.persistence.entity.Task;
import com.workspace.features.user.infrastructure.persistence.entity.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
