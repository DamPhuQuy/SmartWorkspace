package com.workspace.infrastructure.adapter.out.persistence.user;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_profiles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserProfileEntity {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(
        name = "first_name",
        nullable = false
    )
    private String firstName;

    @Column(
        name = "last_name",
        nullable = true
    )
    private String lastName;

    @Column(
        name = "avatar_url",
        nullable = true
    )
    private String avatarUrl;

    @Column(
        name = "bio",
        nullable = true
    )
    private String bio;

    @Column(
        name = "discord_id",
        nullable = true,
        unique = true
    )
    private String discordId;
}
