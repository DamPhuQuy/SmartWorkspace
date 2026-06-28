package com.workspace.domain.model.workspace;

import java.time.Instant;
import java.util.UUID;
import com.workspace.domain.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkSpaceMember {
    private UUID id;
    private WorkSpace workspace;
    private User user;
    private Instant joinedAt;
}
