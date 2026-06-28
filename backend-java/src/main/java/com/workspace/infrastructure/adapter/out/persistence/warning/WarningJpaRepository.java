package com.workspace.infrastructure.adapter.out.persistence.warning;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarningJpaRepository extends JpaRepository<WarningEntity, UUID> {
    List<WarningEntity> findByWorkspaceMemberId(UUID workspaceMemberId);
}
