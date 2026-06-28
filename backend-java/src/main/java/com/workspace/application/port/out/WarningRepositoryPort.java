package com.workspace.application.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.workspace.domain.model.warning.Warning;

public interface WarningRepositoryPort {
    Optional<Warning> findById(UUID id);
    List<Warning> findByWorkspaceMemberId(UUID workspaceMemberId);
    Warning save(Warning warning);
    void deleteById(UUID id);
}
