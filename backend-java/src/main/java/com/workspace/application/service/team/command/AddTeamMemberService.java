package com.workspace.application.service.team.command;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.team.command.AddTeamMemberUseCase;
import com.workspace.application.port.out.team.TeamRepositoryPort;
import com.workspace.application.port.out.workspace.WorkspaceRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.team.Team;
import com.workspace.domain.model.team.TeamMember;
import com.workspace.domain.model.workspace.WorkspaceMember;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddTeamMemberService implements AddTeamMemberUseCase {

    private final TeamRepositoryPort teamRepositoryPort;
    private final WorkspaceRepositoryPort workspaceRepositoryPort;
        
    @Override
    @Transactional
    public TeamMember addTeamMember(Command command) {
        Team team = teamRepositoryPort.findById(command.teamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team with ID " + command.teamId() + " not found"));

        WorkspaceMember member = workspaceRepositoryPort.findMemberById(command.workspaceMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member with ID " + command.workspaceMemberId() + " not found"));

        // Ensure both belong to the same workspace
        if (!team.getWorkspace().getId().equals(member.getWorkspaceId())) {
            throw new DomainException("Team and workspace member must belong to the same workspace");
        }

        if (teamRepositoryPort.existsMemberByTeamIdAndWorkspaceMemberId(command.teamId(), command.workspaceMemberId())) {
            throw new DomainException("Workspace member is already in this team");
        }

        TeamMember teamMember = TeamMember.builder()
                .id(UUID.randomUUID())
                .team(team)
                .workspaceMember(member)
                .joinedAt(Instant.now())
                .build();

        return teamRepositoryPort.saveMember(teamMember);
    }
}
