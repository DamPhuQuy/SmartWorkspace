package com.workspace.application.service.team;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.team.RemoveTeamMemberUseCase;
import com.workspace.application.port.out.team.TeamMemberRepositoryPort;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.team.TeamMember;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RemoveTeamMemberService implements RemoveTeamMemberUseCase {

    private final TeamMemberRepositoryPort teamMemberRepositoryPort;

    @Override
    @Transactional
    public void removeTeamMember(Command command) {
        TeamMember teamMember = teamMemberRepositoryPort.findByTeamIdAndWorkspaceMemberId(command.teamId(), command.workspaceMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace member is not a member of this team"));

        teamMemberRepositoryPort.deleteById(teamMember.getId());
    }
}
