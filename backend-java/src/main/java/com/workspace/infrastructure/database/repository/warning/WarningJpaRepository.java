package com.workspace.infrastructure.database.repository.warning;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.workspace.infrastructure.database.entity.warning.WarningEntity;

public interface WarningJpaRepository extends JpaRepository<WarningEntity, UUID> {
    List<WarningEntity> findByWorkspaceMemberId(UUID workspaceMemberId);
}
