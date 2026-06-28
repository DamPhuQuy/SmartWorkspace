package com.workspace.application.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.team.Team;

public interface TeamRepositoryPort {
    Optional<Team> findById(UUID id);
    Optional<Team> findByWorkspaceIdAndName(UUID workspaceId, String name);
    List<Team> findByWorkspaceId(UUID workspaceId);
    Team save(Team team);
    boolean existsByWorkspaceIdAndName(UUID workspaceId, String name);
    void deleteById(UUID id);
}
