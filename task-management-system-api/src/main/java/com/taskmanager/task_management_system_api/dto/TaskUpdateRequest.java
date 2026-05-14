package com.taskmanager.task_management_system_api.dto;

import com.taskmanager.task_management_system_api.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Request DTO for updating an existing task
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskUpdateRequest {

    private String title;

    private String description;

    private TaskStatus status;

    private LocalDate dueDate;
}
