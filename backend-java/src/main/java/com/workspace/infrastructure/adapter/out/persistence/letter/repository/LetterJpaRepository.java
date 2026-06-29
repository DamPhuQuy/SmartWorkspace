package com.workspace.infrastructure.adapter.out.persistence.letter.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workspace.infrastructure.adapter.out.persistence.letter.entity.LetterEntity;

public interface LetterJpaRepository extends JpaRepository<LetterEntity, UUID> {
    List<LetterEntity> findByWorkspaceMemberId(UUID workspaceMemberId);
    List<LetterEntity> findByWorkspaceMemberIdAndLetterType(UUID workspaceMemberId, String letterType);
}
