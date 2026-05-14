package com.taskmanager.task_management_system_api.exception;

/**
 * Custom exception for validation errors
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
