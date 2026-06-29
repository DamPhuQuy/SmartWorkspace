package com.workspace.application.service.team.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.team.command.RemoveTeamMemberUseCase;
import com.workspace.application.port.out.team.TeamRepositoryPort;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.team.TeamMember;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RemoveTeamMemberService implements RemoveTeamMemberUseCase {

    private final TeamRepositoryPort teamRepositoryPort;

    @Override
    @Transactional
    public void removeTeamMember(Command command) {
        TeamMember teamMember = teamRepositoryPort.findMemberByTeamIdAndWorkspaceMemberId(command.teamId(), command.workspaceMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member is not a member of this team"));

        teamRepositoryPort.deleteMemberById(teamMember.getId());
    }
}
