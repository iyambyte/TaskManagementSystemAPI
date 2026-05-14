package com.taskmanager.task_management_system_api.util;

import com.taskmanager.task_management_system_api.exception.ValidationException;
import java.time.LocalDate;

/**
 * Utility class for validation operations
 */
public class ValidationUtil {

    /**
     * Validate that due date is in the future
     */
    public static void validateFutureDate(LocalDate dueDate) {
        if (dueDate == null) {
            throw new ValidationException("Due date cannot be null");
        }

        if (dueDate.isBefore(LocalDate.now())) {
            throw new ValidationException("Due date must be in the future");
        }
    }

    /**
     * Validate that task ID is not blank
     */
    public static void validateTaskId(String taskId) {
        if (taskId == null || taskId.trim().isEmpty()) {
            throw new ValidationException("Task ID cannot be null or empty");
        }
    }

    /**
     * Validate that title is not blank
     */
    public static void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new ValidationException("Title cannot be null or empty");
        }
    }

    /**
     * Validate page and page size for pagination
     */
    public static void validatePagination(int page, int pageSize) {
        if (page < 0) {
            throw new ValidationException("Page number cannot be negative");
        }
        if (pageSize <= 0 || pageSize > 100) {
            throw new ValidationException("Page size must be between 1 and 100");
        }
    }
}
