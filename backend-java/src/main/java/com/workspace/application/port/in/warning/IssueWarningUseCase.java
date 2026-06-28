package com.workspace.application.port.in.warning;

import java.util.UUID;
import com.workspace.domain.model.warning.Warning;

public interface IssueWarningUseCase {
    Warning issueWarning(Command command);

    record Command(
        UUID workspaceMemberId,
        String warningType,
        String description
    ) {}
}
