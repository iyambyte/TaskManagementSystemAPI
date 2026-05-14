package com.taskmanager.task_management_system_api.service;

import com.taskmanager.task_management_system_api.dto.PaginatedResponse;
import com.taskmanager.task_management_system_api.dto.TaskCreateRequest;
import com.taskmanager.task_management_system_api.dto.TaskDTO;
import com.taskmanager.task_management_system_api.dto.TaskUpdateRequest;
import com.taskmanager.task_management_system_api.enums.TaskStatus;

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
