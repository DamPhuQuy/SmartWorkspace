package com.workspace.features.task.infrastructure.web;

import com.workspace.features.task.application.dto.*;
import com.workspace.features.task.application.service.*;
import com.workspace.features.task.infrastructure.persistence.entity.*;
import com.workspace.features.task.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.task.infrastructure.web.dto.*;

import com.workspace.features.user.infrastructure.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/api/projects/{projectId}/board")
    @PreAuthorize("hasAuthority('*') or hasAuthority('task:read')")
    public ResponseEntity<BoardDto> getBoard(
            @PathVariable UUID projectId,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(taskService.getBoardForProject(projectId, user));
    }

    @PostMapping("/api/boards/{boardId}/columns")
    @PreAuthorize("hasAuthority('*') or hasAuthority('board:update')")
    public ResponseEntity<BoardColumnDto> createColumn(
            @PathVariable UUID boardId,
            @RequestParam String name,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(taskService.createColumn(boardId, name, user));
    }

    @PostMapping("/api/projects/{projectId}/columns/{columnId}/tasks")
    @PreAuthorize("hasAuthority('*') or hasAuthority('task:create')")
    public ResponseEntity<TaskDto> createTask(
            @PathVariable UUID projectId,
            @PathVariable UUID columnId,
            @RequestBody CreateTaskRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(taskService.createTask(projectId, columnId, request, user));
    }

    @PutMapping("/api/tasks/{taskId}")
    @PreAuthorize("hasAuthority('*') or hasAuthority('task:update')")
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable UUID taskId,
            @RequestBody UpdateTaskRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(taskService.updateTask(taskId, request, user));
    }

    @DeleteMapping("/api/tasks/{taskId}")
    @PreAuthorize("hasAuthority('*') or hasAuthority('task:delete')")
    public ResponseEntity<Void> deleteTask(
            @PathVariable UUID taskId,
            @AuthenticationPrincipal User user
    ) {
        taskService.deleteTask(taskId, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/tasks/{taskId}/subtasks")
    @PreAuthorize("hasAuthority('*') or hasAuthority('task:read')")
    public ResponseEntity<List<SubtaskDto>> getSubtasks(
            @PathVariable UUID taskId,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(taskService.getSubtasks(taskId, user));
    }

    @PostMapping("/api/tasks/{taskId}/subtasks")
    @PreAuthorize("hasAuthority('*') or hasAuthority('task:create')")
    public ResponseEntity<SubtaskDto> createSubtask(
            @PathVariable UUID taskId,
            @RequestBody CreateSubtaskRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(taskService.createSubtask(taskId, request, user));
    }

    @PutMapping("/api/subtasks/{subtaskId}")
    @PreAuthorize("hasAuthority('*') or hasAuthority('task:update')")
    public ResponseEntity<SubtaskDto> toggleSubtask(
            @PathVariable UUID subtaskId,
            @RequestParam boolean isCompleted,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(taskService.toggleSubtask(subtaskId, isCompleted, user));
    }

    @DeleteMapping("/api/subtasks/{subtaskId}")
    @PreAuthorize("hasAuthority('*') or hasAuthority('task:delete')")
    public ResponseEntity<Void> deleteSubtask(
            @PathVariable UUID subtaskId,
            @AuthenticationPrincipal User user
    ) {
        taskService.deleteSubtask(subtaskId, user);
        return ResponseEntity.noContent().build();
    }
}
