package com.workspace.application.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.workspace.WorkSpaceMember;

public interface WorkSpaceMemberRepositoryPort {
    Optional<WorkSpaceMember> findById(UUID id);
    Optional<WorkSpaceMember> findByWorkspaceIdAndUserId(UUID workspaceId, UUID userId);
    List<WorkSpaceMember> findByWorkspaceId(UUID workspaceId);
    List<WorkSpaceMember> findByUserId(UUID userId);
    WorkSpaceMember save(WorkSpaceMember workSpaceMember);
    boolean existsByWorkspaceIdAndUserId(UUID workspaceId, UUID userId);
    void deleteById(UUID id);
}
