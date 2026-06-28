package com.workspace.application.service.team;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.team.AddTeamMemberUseCase;
import com.workspace.application.port.out.team.TeamMemberRepositoryPort;
import com.workspace.application.port.out.team.TeamRepositoryPort;
import com.workspace.application.port.out.workspace.WorkSpaceMemberRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.team.Team;
import com.workspace.domain.model.team.TeamMember;
import com.workspace.domain.model.workspace.WorkSpaceMember;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddTeamMemberService implements AddTeamMemberUseCase {

    private final TeamRepositoryPort teamRepositoryPort;
    private final WorkSpaceMemberRepositoryPort workSpaceMemberRepositoryPort;
    private final TeamMemberRepositoryPort teamMemberRepositoryPort;

    @Override
    @Transactional
    public TeamMember addTeamMember(Command command) {
        Team team = teamRepositoryPort.findById(command.teamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team with ID " + command.teamId() + " not found"));

        WorkSpaceMember member = workSpaceMemberRepositoryPort.findById(command.workspaceMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member with ID " + command.workspaceMemberId() + " not found"));

        // Ensure both belong to the same workspace
        if (!team.getWorkspace().getId().equals(member.getWorkspace().getId())) {
            throw new DomainException("Team and workspace member must belong to the same workspace");
        }

        if (teamMemberRepositoryPort.existsByTeamIdAndWorkspaceMemberId(command.teamId(), command.workspaceMemberId())) {
            throw new DomainException("Workspace member is already in this team");
        }

        TeamMember teamMember = TeamMember.builder()
                .id(UUID.randomUUID())
                .team(team)
                .workspaceMember(member)
                .joinedAt(Instant.now())
                .build();

        return teamMemberRepositoryPort.save(teamMember);
    }
}
