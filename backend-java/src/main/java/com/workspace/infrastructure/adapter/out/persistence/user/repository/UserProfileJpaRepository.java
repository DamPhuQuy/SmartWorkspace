package com.workspace.infrastructure.adapter.out.persistence.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workspace.infrastructure.adapter.out.persistence.user.entity.UserProfileEntity;

public interface UserProfileJpaRepository extends JpaRepository<UserProfileEntity, UUID> {
}
