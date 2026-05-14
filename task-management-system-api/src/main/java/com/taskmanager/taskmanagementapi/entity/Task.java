package com.taskmanager.taskmanagementapi.entity;

import com.taskmanager.taskmanagementapi.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Task Entity representing a task in the system
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Generate a new Task with default values
     */
    public static Task create(String title, String description, LocalDate dueDate) {
        return Task.builder()
                .id(UUID.randomUUID().toString())
                .title(title)
                .description(description)
                .status(TaskStatus.PENDING)
                .dueDate(dueDate)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    /**
     * Update task timestamps
     */
    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }
}
