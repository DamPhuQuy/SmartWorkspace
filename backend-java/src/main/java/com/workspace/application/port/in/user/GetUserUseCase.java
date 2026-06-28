package com.workspace.application.port.in.user;

import java.util.UUID;
import com.workspace.domain.model.user.User;

public interface GetUserUseCase {
    User getUserById(UUID id);
}
