package com.workspace.infrastructure.database.entity.workspace;

import com.workspace.infrastructure.database.entity.user.RoleEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "workspace_member_roles",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_workspace_member_role",
            columnNames = {"workspace_member_id", "role_id"}
        )
    }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class WorkspaceMemberRoleEntity {

    @Column(
        name = "workspace_member_id",
        nullable = false
    )
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private WorkSpaceMemberEntity workspaceMember;

    @Column(
        name = "role_id",
        nullable = false
    )
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private RoleEntity role;
}
