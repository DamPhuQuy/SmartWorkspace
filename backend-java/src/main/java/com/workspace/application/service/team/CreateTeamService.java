package com.workspace.application.service.team;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.team.CreateTeamUseCase;
import com.workspace.application.port.out.team.TeamRepositoryPort;
import com.workspace.application.port.out.workspace.WorkSpaceRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.team.Team;
import com.workspace.domain.model.workspace.WorkSpace;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateTeamService implements CreateTeamUseCase {

    private final TeamRepositoryPort teamRepositoryPort;
    private final WorkSpaceRepositoryPort workSpaceRepositoryPort;

    @Override
    @Transactional
    public Team createTeam(Command command) {
        WorkSpace workspace = workSpaceRepositoryPort.findById(command.workspaceId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace with ID " + command.workspaceId() + " not found"));

        if (teamRepositoryPort.existsByWorkspaceIdAndName(command.workspaceId(), command.name())) {
            throw new DomainException("Team with name " + command.name() + " already exists in this workspace");
        }

        Team team = Team.builder()
                .id(UUID.randomUUID())
                .workspace(workspace)
                .name(command.name())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return teamRepositoryPort.save(team);
    }
}
