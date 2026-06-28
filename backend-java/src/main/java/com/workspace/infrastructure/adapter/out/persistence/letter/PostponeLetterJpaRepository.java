package com.workspace.infrastructure.adapter.out.persistence.letter;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostponeLetterJpaRepository extends JpaRepository<PostponeLetterEntity, UUID> {
}
