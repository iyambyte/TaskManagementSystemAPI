package com.taskmanager.taskmanagementapi.service;

import com.taskmanager.taskmanagementapi.dto.PaginatedResponse;
import com.taskmanager.taskmanagementapi.dto.TaskCreateRequest;
import com.taskmanager.taskmanagementapi.dto.TaskDTO;
import com.taskmanager.taskmanagementapi.dto.TaskUpdateRequest;
import com.taskmanager.taskmanagementapi.enums.TaskStatus;

/**
 * Service interface for task management operations
 */
public interface TaskService {

    /**
     * Create a new task
     */
    TaskDTO createTask(TaskCreateRequest request);

    /**
     * Get a task by ID
     */
    TaskDTO getTaskById(String id);

    /**
     * Update an existing task
     */
    TaskDTO updateTask(String id, TaskUpdateRequest request);

    /**
     * Delete a task by ID
     */
    void deleteTask(String id);

    /**
     * Get all tasks with pagination and optional filtering
     */
    PaginatedResponse<TaskDTO> getAllTasks(int page, int pageSize, TaskStatus status);

    /**
     * Get all tasks (unfiltered and unpaginated)
     */
    PaginatedResponse<TaskDTO> getAllTasks(int page, int pageSize);
}
