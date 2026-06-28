package com.workspace.application.port.out.workspace;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.workspace.WorkspaceMemberRole;

public interface WorkspaceMemberRoleRepositoryPort {
    WorkspaceMemberRole save(WorkspaceMemberRole workspaceMemberRole);
    Optional<WorkspaceMemberRole> findByWorkspaceMemberIdAndRoleId(UUID workspaceMemberId, UUID roleId);
    List<WorkspaceMemberRole> findByWorkspaceMemberId(UUID workspaceMemberId);
    void deleteById(UUID id);
    boolean existsByWorkspaceMemberIdAndRoleId(UUID workspaceMemberId, UUID roleId);
}
