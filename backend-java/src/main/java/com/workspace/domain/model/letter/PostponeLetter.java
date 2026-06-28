package com.workspace.domain.model.letter;

import java.time.Instant;
import java.util.UUID;
import com.workspace.domain.model.assignment.Assignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostponeLetter {
    private UUID letterId;
    private Letter letter;
    private Assignment assignment;
    private String assignmentSnapshot;
    private Instant oldDeadline;
    private Instant requestedDeadline;
}
