package com.workspace.infrastructure.adapter.out.persistence.letter.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workspace.infrastructure.adapter.out.persistence.letter.entity.AbsenceLetterEntity;

public interface AbsenceLetterJpaRepository extends JpaRepository<AbsenceLetterEntity, UUID> {
}
