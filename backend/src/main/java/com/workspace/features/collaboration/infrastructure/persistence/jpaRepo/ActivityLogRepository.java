package com.workspace.features.collaboration.infrastructure.persistence.jpaRepo;

import com.workspace.features.collaboration.application.dto.*;
import com.workspace.features.collaboration.application.service.*;
import com.workspace.features.collaboration.infrastructure.persistence.entity.*;
import com.workspace.features.collaboration.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.collaboration.infrastructure.web.dto.*;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, UUID> {
    List<ActivityLog> findByWorkspaceIdOrderByCreatedAtDesc(UUID workspaceId);
}
