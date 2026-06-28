package com.workspace.domain.model.workspace;

import java.util.UUID;
import com.workspace.domain.model.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceMemberRole {
    private UUID id;
    private WorkspaceMember workspaceMember;
    private Role role;
}
