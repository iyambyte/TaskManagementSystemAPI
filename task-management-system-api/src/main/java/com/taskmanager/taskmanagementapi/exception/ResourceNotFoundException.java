package com.taskmanager.taskmanagementapi.exception;

/**
 * Custom exception for when a resource is not found
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
