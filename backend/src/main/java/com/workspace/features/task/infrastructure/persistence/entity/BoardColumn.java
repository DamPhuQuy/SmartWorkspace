package com.workspace.features.task.infrastructure.persistence.entity;

import com.workspace.features.task.application.dto.*;
import com.workspace.features.task.application.service.*;
import com.workspace.features.task.infrastructure.persistence.entity.*;
import com.workspace.features.task.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.task.infrastructure.web.dto.*;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "board_columns", uniqueConstraints = {
    @UniqueConstraint(name = "unique_board_column_position", columnNames = {"board_id", "position"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int position;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
    }
}
