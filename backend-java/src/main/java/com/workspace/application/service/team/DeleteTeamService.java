package com.workspace.application.service.team;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.team.DeleteTeamUseCase;
import com.workspace.application.port.out.team.TeamRepositoryPort;
import com.workspace.domain.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteTeamService implements DeleteTeamUseCase {

    private final TeamRepositoryPort teamRepositoryPort;

    @Override
    @Transactional
    public void deleteTeam(UUID id) {
        if (!teamRepositoryPort.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Team with ID " + id + " not found");
        }
        teamRepositoryPort.deleteById(id);
    }
}
