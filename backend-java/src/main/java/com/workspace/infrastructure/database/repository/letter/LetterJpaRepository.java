package com.workspace.infrastructure.database.repository.letter;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.workspace.infrastructure.database.entity.letter.LetterEntity;

public interface LetterJpaRepository extends JpaRepository<LetterEntity, UUID> {
    List<LetterEntity> findByWorkspaceMemberId(UUID workspaceMemberId);
    List<LetterEntity> findByWorkspaceMemberIdAndLetterType(UUID workspaceMemberId, String letterType);
}
