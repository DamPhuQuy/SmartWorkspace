package com.workspace.infrastructure.database.repository.letter;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.workspace.infrastructure.database.entity.letter.PostponeLetterEntity;

public interface PostponeLetterJpaRepository extends JpaRepository<PostponeLetterEntity, UUID> {
}
