package com.taskmanager.task_management_system_api.dto;

import com.taskmanager.task_management_system_api.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Task Data Transfer Object for API responses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
