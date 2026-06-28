package com.workspace.infrastructure.adapter.out.persistence.letter;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsenceLetterJpaRepository extends JpaRepository<AbsenceLetterEntity, UUID> {
}
