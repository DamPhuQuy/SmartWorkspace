package com.workspace.infrastructure.adapter.out.persistence.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.workspace.infrastructure.adapter.out.persistence.user.entity.UserEntity;
import com.workspace.infrastructure.adapter.out.persistence.user.entity.UserProfileEntity;


public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    @Query("""
        select p
        from UserEntity u
        join u.userProfile p
        where u.id = :userId
    """)
    Optional<UserProfileEntity> findProfileByUserId(@Param("userId") UUID userId);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
