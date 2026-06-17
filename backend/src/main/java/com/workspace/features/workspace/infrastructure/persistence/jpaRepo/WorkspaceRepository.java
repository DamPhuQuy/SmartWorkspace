package com.workspace.features.workspace.infrastructure.persistence.jpaRepo;

import com.workspace.features.workspace.application.dto.*;
import com.workspace.features.workspace.application.service.*;
import com.workspace.features.workspace.infrastructure.persistence.entity.*;
import com.workspace.features.workspace.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.workspace.infrastructure.web.dto.*;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {
    Optional<Workspace> findBySlug(String slug);
    boolean existsBySlug(String slug);
}
