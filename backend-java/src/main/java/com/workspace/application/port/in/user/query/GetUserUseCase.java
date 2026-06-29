package com.workspace.application.port.in.user.query;

import java.util.UUID;
import com.workspace.domain.model.user.User;

public interface GetUserUseCase {
    User getUserById(UUID id);
}
