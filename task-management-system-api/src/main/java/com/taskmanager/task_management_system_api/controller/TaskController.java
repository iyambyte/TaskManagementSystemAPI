package com.taskmanager.task_management_system_api.controller;

import com.taskmanager.task_management_system_api.dto.PaginatedResponse;
import com.taskmanager.task_management_system_api.dto.TaskCreateRequest;
import com.taskmanager.task_management_system_api.dto.TaskDTO;
import com.taskmanager.task_management_system_api.dto.TaskUpdateRequest;
import com.taskmanager.task_management_system_api.enums.TaskStatus;
import com.taskmanager.task_management_system_api.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for task management operations
 */
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * Create a new task
     * POST /tasks
     */
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskCreateRequest request) {
        TaskDTO createdTask = taskService.createTask(request);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    /**
     * Get a task by ID
     * GET /tasks/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable String id) {
        TaskDTO task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    /**
     * Update an existing task
     * PUT /tasks/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable String id,
            @RequestBody TaskUpdateRequest request) {
        TaskDTO updatedTask = taskService.updateTask(id, request);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    /**
     * Delete a task by ID
     * DELETE /tasks/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Get all tasks with pagination and optional filtering
     * GET /tasks?page=0&pageSize=10&status=PENDING
     */
    @GetMapping
    public ResponseEntity<PaginatedResponse<TaskDTO>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) TaskStatus status) {

        PaginatedResponse<TaskDTO> response = taskService.getAllTasks(page, pageSize, status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Task Management API is running!");
    }
}
