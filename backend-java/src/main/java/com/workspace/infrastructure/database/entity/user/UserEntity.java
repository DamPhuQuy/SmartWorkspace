package com.workspace.infrastructure.database.entity.user;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @OneToOne(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private UserProfileEntity userProfile;

    @Column(
        name = "email",
        nullable = false,
        unique = true
    )
    private String email;

    @Column(
        name = "password_hash",
        nullable = false
    )
    private String passwordHash;

    @Column(
        name = "phone",
        nullable = true,
        unique = true
    )
    private String phone;

    @Column(
        name = "online_status",
        nullable = false
    )
    private boolean onlineStatus;

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
