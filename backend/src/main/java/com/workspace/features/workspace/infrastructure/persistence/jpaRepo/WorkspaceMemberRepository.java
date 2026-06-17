package com.workspace.features.workspace.infrastructure.persistence.jpaRepo;

import com.workspace.features.workspace.application.dto.*;
import com.workspace.features.workspace.application.service.*;
import com.workspace.features.workspace.infrastructure.persistence.entity.*;
import com.workspace.features.workspace.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.workspace.infrastructure.web.dto.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, UUID> {
    List<WorkspaceMember> findByWorkspaceId(UUID workspaceId);
    List<WorkspaceMember> findByUserId(UUID userId);
    @Query("""
            select distinct member
            from WorkspaceMember member
            left join fetch member.role role
            left join fetch role.permissionSet
            where member.user.id = :userId
            """)
    List<WorkspaceMember> findByUserIdWithRolePermissions(@Param("userId") UUID userId);
    Optional<WorkspaceMember> findByWorkspaceIdAndUserId(UUID workspaceId, UUID userId);
    boolean existsByWorkspaceIdAndUserId(UUID workspaceId, UUID userId);
}
