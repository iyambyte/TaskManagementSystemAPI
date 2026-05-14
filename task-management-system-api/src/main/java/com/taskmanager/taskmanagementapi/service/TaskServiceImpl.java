package com.taskmanager.taskmanagementapi.service;

import com.taskmanager.taskmanagementapi.dto.PaginatedResponse;
import com.taskmanager.taskmanagementapi.dto.TaskCreateRequest;
import com.taskmanager.taskmanagementapi.dto.TaskDTO;
import com.taskmanager.taskmanagementapi.dto.TaskUpdateRequest;
import com.taskmanager.taskmanagementapi.entity.Task;
import com.taskmanager.taskmanagementapi.enums.TaskStatus;
import com.taskmanager.taskmanagementapi.exception.ResourceNotFoundException;
import com.taskmanager.taskmanagementapi.repository.TaskRepository;
import com.taskmanager.taskmanagementapi.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for task management operations
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public TaskDTO createTask(TaskCreateRequest request) {
        // Validate title is not blank
        ValidationUtil.validateTitle(request.getTitle());

        // Validate due date is in the future
        ValidationUtil.validateFutureDate(request.getDueDate());

        // Create and save the task
        Task task = Task.create(
                request.getTitle(),
                request.getDescription(),
                request.getDueDate());

        // Override status if provided
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        Task savedTask = taskRepository.save(task);
        return convertToDTO(savedTask);
    }

    @Override
    public TaskDTO getTaskById(String id) {
        ValidationUtil.validateTaskId(id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        return convertToDTO(task);
    }

    @Override
    public TaskDTO updateTask(String id, TaskUpdateRequest request) {
        ValidationUtil.validateTaskId(id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        // Update fields if provided
        if (request.getTitle() != null && !request.getTitle().trim().isEmpty()) {
            task.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }

        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        if (request.getDueDate() != null) {
            ValidationUtil.validateFutureDate(request.getDueDate());
            task.setDueDate(request.getDueDate());
        }

        // Update timestamp
        task.updateTimestamp();

        Task updatedTask = taskRepository.save(task);
        return convertToDTO(updatedTask);
    }

    @Override
    public void deleteTask(String id) {
        ValidationUtil.validateTaskId(id);

        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }

        taskRepository.deleteById(id);
    }

    @Override
    public PaginatedResponse<TaskDTO> getAllTasks(int page, int pageSize, TaskStatus status) {
        ValidationUtil.validatePagination(page, pageSize);

        List<Task> tasks;
        long totalElements;

        if (status != null) {
            tasks = taskRepository.findByStatusSortedByDueDate(status);
            totalElements = tasks.size();
        } else {
            tasks = taskRepository.findAllSortedByDueDate();
            totalElements = tasks.size();
        }

        // Apply pagination
        int startIndex = page * pageSize;
        int endIndex;
        List<TaskDTO> paginatedTasks;

        if (startIndex >= totalElements) {
            paginatedTasks = java.util.List.of();
            endIndex = (int) totalElements; // Used later for hasNext
        } else {
            endIndex = Math.min(startIndex + pageSize, (int) totalElements);
            paginatedTasks = tasks.subList(startIndex, endIndex)
                    .stream()
                    .map(this::convertToDTO)
                    .toList();
        }

        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        return PaginatedResponse.<TaskDTO>builder()
                .content(paginatedTasks)
                .page(page)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .hasNext(endIndex < totalElements)
                .hasPrevious(page > 0)
                .build();
    }

    @Override
    public PaginatedResponse<TaskDTO> getAllTasks(int page, int pageSize) {
        return getAllTasks(page, pageSize, null);
    }

    /**
     * Convert Task entity to TaskDTO
     */
    private TaskDTO convertToDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
