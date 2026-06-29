package com.workspace.infrastructure.adapter.out.persistence.letter.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workspace.infrastructure.adapter.out.persistence.letter.entity.LateLetterEntity;

public interface LateLetterJpaRepository extends JpaRepository<LateLetterEntity, UUID> {
}
