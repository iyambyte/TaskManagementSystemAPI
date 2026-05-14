package com.taskmanager.task_management_system_api.repository;

import com.taskmanager.task_management_system_api.entity.Task;
import com.taskmanager.task_management_system_api.enums.TaskStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * In-memory repository implementation for Task entity
 */
@Repository
public class TaskRepository {

    private final Map<String, Task> taskStore = new LinkedHashMap<>();

    /**
     * Save or update a task
     */
    public Task save(Task task) {
        taskStore.put(task.getId(), task);
        return task;
    }

    /**
     * Find a task by ID
     */
    public Optional<Task> findById(String id) {
        return Optional.ofNullable(taskStore.get(id));
    }

    /**
     * Find all tasks
     */
    public List<Task> findAll() {
        return new ArrayList<>(taskStore.values());
    }

    /**
     * Find tasks by status
     */
    public List<Task> findByStatus(TaskStatus status) {
        return taskStore.values()
                .stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }

    /**
     * Find all tasks sorted by due date
     */
    public List<Task> findAllSortedByDueDate() {
        return taskStore.values()
                .stream()
                .sorted(Comparator.comparing(Task::getDueDate))
                .collect(Collectors.toList());
    }

    /**
     * Find tasks by status and sort by due date
     */
    public List<Task> findByStatusSortedByDueDate(TaskStatus status) {
        return taskStore.values()
                .stream()
                .filter(task -> task.getStatus() == status)
                .sorted(Comparator.comparing(Task::getDueDate))
                .collect(Collectors.toList());
    }

    /**
     * Delete a task by ID
     */
    public boolean deleteById(String id) {
        return taskStore.remove(id) != null;
    }

    /**
     * Check if a task exists
     */
    public boolean existsById(String id) {
        return taskStore.containsKey(id);
    }

    /**
     * Get total count of tasks
     */
    public long count() {
        return taskStore.size();
    }

    /**
     * Get count of tasks by status
     */
    public long countByStatus(TaskStatus status) {
        return taskStore.values()
                .stream()
                .filter(task -> task.getStatus() == status)
                .count();
    }

    /**
     * Delete all tasks (for testing purposes)
     */
    public void deleteAll() {
        taskStore.clear();
    }

    /**
     * Find tasks due on a specific date
     */
    public List<Task> findByDueDate(LocalDate dueDate) {
        return taskStore.values()
                .stream()
                .filter(task -> task.getDueDate().equals(dueDate))
                .sorted(Comparator.comparing(Task::getDueDate))
                .collect(Collectors.toList());
    }
}
