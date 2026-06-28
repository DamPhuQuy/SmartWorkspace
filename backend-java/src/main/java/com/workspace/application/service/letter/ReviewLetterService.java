package com.workspace.application.service.letter;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workspace.application.port.in.letter.ReviewLetterUseCase;
import com.workspace.application.port.out.letter.LetterRepositoryPort;
import com.workspace.domain.exception.DomainException;
import com.workspace.domain.exception.ResourceNotFoundException;
import com.workspace.domain.model.letter.Letter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewLetterService implements ReviewLetterUseCase {

    private final LetterRepositoryPort letterRepositoryPort;

    @Override
    @Transactional
    public Letter reviewLetter(Command command) {
        Letter letter = letterRepositoryPort.findById(command.letterId())
                .orElseThrow(() -> new ResourceNotFoundException("Letter with ID " + command.letterId() + " not found"));

        if (!"pending".equalsIgnoreCase(letter.getStatus())) {
            throw new DomainException("Only pending letters can be reviewed");
        }

        String newStatus = command.status().toLowerCase();
        if (!"approved".equals(newStatus) && !"rejected".equals(newStatus)) {
            throw new DomainException("Status must be either 'approved' or 'rejected'");
        }

        Letter updatedLetter = Letter.builder()
                .id(letter.getId())
                .workspaceMember(letter.getWorkspaceMember())
                .letterType(letter.getLetterType())
                .description(letter.getDescription())
                .status(newStatus)
                .createdAt(letter.getCreatedAt())
                .updatedAt(Instant.now())
                .build();

        return letterRepositoryPort.save(updatedLetter);
    }
}
