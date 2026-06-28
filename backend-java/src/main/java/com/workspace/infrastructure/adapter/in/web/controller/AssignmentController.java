package com.workspace.infrastructure.adapter.in.web.controller;

import org.springframework.web.bind.annotation.*;

import com.workspace.application.port.in.assignment.AssignAssignmentUseCase;
import com.workspace.application.port.in.assignment.CreateAssignmentUseCase;
import com.workspace.application.port.in.assignment.SubmitAssignmentUseCase;
import com.workspace.domain.model.assignment.Assignment;
import com.workspace.domain.model.assignment.Assignee;
import com.workspace.domain.model.assignment.Submission;
import com.workspace.infrastructure.adapter.in.web.common.ApiResponse;
import com.workspace.infrastructure.adapter.in.web.dto.AssignmentDto;
import com.workspace.infrastructure.adapter.in.web.mapper.AssignmentWebMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
@Tag(name = "Assignments", description = "Assignment definition, assignment delegation, and submission management")
public class AssignmentController {

    private final CreateAssignmentUseCase createAssignmentUseCase;
    private final AssignAssignmentUseCase assignAssignmentUseCase;
    private final SubmitAssignmentUseCase submitAssignmentUseCase;

    @PostMapping
    @Operation(summary = "Create assignment", description = "Creates a new assignment in a specific workspace")
    public ApiResponse<AssignmentDto.AssignmentResponse> createAssignment(@RequestBody AssignmentDto.CreateAssignmentRequest request) {
        CreateAssignmentUseCase.Command command = new CreateAssignmentUseCase.Command(
            request.workspaceId(),
            request.title(),
            request.description(),
            request.deadline(),
            request.createdById()
        );
        Assignment assignment = createAssignmentUseCase.createAssignment(command);
        return ApiResponse.success("Assignment created successfully", AssignmentWebMapper.toResponse(assignment));
    }

    @PostMapping("/assign")
    @Operation(summary = "Assign assignment", description = "Assigns an assignment to a workspace member")
    public ApiResponse<AssignmentDto.AssigneeResponse> assignAssignment(@RequestBody AssignmentDto.AssignAssignmentRequest request) {
        AssignAssignmentUseCase.Command command = new AssignAssignmentUseCase.Command(
            request.assignmentId(),
            request.workspaceMemberId()
        );
        Assignee assignee = assignAssignmentUseCase.assignAssignment(command);
        return ApiResponse.success("Assignment assigned successfully", AssignmentWebMapper.toAssigneeResponse(assignee));
    }

    @PostMapping("/submit")
    @Operation(summary = "Submit assignment", description = "Submit a solution/URL for an assignee assignment")
    public ApiResponse<AssignmentDto.SubmissionResponse> submitAssignment(@RequestBody AssignmentDto.SubmitAssignmentRequest request) {
        SubmitAssignmentUseCase.Command command = new SubmitAssignmentUseCase.Command(
            request.assignmentAssigneeId(),
            request.submissionUrl()
        );
        Submission submission = submitAssignmentUseCase.submitAssignment(command);
        return ApiResponse.success("Assignment submitted successfully", AssignmentWebMapper.toSubmissionResponse(submission));
    }
}
