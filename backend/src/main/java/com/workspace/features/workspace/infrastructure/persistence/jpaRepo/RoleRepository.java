package com.workspace.features.workspace.infrastructure.persistence.jpaRepo;

import com.workspace.features.workspace.application.dto.*;
import com.workspace.features.workspace.application.service.*;
import com.workspace.features.workspace.infrastructure.persistence.entity.*;
import com.workspace.features.workspace.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.workspace.infrastructure.web.dto.*;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    List<Role> findByWorkspaceId(UUID workspaceId);
    Optional<Role> findByWorkspaceIdAndName(UUID workspaceId, String name);
}
