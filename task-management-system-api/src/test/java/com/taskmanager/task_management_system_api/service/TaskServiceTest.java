package com.taskmanager.task_management_system_api.service;

import com.taskmanager.task_management_system_api.dto.PaginatedResponse;
import com.taskmanager.task_management_system_api.dto.TaskCreateRequest;
import com.taskmanager.task_management_system_api.dto.TaskDTO;
import com.taskmanager.task_management_system_api.dto.TaskUpdateRequest;
import com.taskmanager.task_management_system_api.entity.Task;
import com.taskmanager.task_management_system_api.enums.TaskStatus;
import com.taskmanager.task_management_system_api.exception.ResourceNotFoundException;
import com.taskmanager.task_management_system_api.exception.ValidationException;
import com.taskmanager.task_management_system_api.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TaskService
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("TaskService Unit Tests")
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private TaskCreateRequest validCreateRequest;
    private Task mockTask;

    @BeforeEach
    void setUp() {
        LocalDate futureDate = LocalDate.now().plusDays(7);

        validCreateRequest = TaskCreateRequest.builder()
                .title("Test Task")
                .description("Test Description")
                .dueDate(futureDate)
                .status(TaskStatus.PENDING)
                .build();

        mockTask = Task.builder()
                .id("task-1")
                .title("Test Task")
                .description("Test Description")
                .status(TaskStatus.PENDING)
                .dueDate(futureDate)
                .build();
    }

    // ========== CREATE TASK TESTS ==========

    @Test
    @DisplayName("Should create task successfully with valid request")
    void testCreateTaskSuccess() {
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        TaskDTO result = taskService.createTask(validCreateRequest);

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        assertEquals("Test Description", result.getDescription());
        assertEquals(TaskStatus.PENDING, result.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Should throw ValidationException when title is blank")
    void testCreateTaskWithBlankTitle() {
        validCreateRequest.setTitle("");

        assertThrows(ValidationException.class, () -> taskService.createTask(validCreateRequest));
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Should throw ValidationException when title is null")
    void testCreateTaskWithNullTitle() {
        validCreateRequest.setTitle(null);

        assertThrows(ValidationException.class, () -> taskService.createTask(validCreateRequest));
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Should throw ValidationException when due date is in past")
    void testCreateTaskWithPastDueDate() {
        validCreateRequest.setDueDate(LocalDate.now().minusDays(1));

        assertThrows(ValidationException.class, () -> taskService.createTask(validCreateRequest));
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Should throw ValidationException when due date is null")
    void testCreateTaskWithNullDueDate() {
        validCreateRequest.setDueDate(null);

        assertThrows(ValidationException.class, () -> taskService.createTask(validCreateRequest));
        verify(taskRepository, never()).save(any(Task.class));
    }

    // ========== GET TASK TESTS ==========

    @Test
    @DisplayName("Should retrieve task successfully by ID")
    void testGetTaskByIdSuccess() {
        when(taskRepository.findById("task-1")).thenReturn(Optional.of(mockTask));

        TaskDTO result = taskService.getTaskById("task-1");

        assertNotNull(result);
        assertEquals("task-1", result.getId());
        assertEquals("Test Task", result.getTitle());
        verify(taskRepository, times(1)).findById("task-1");
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when task not found")
    void testGetTaskByIdNotFound() {
        when(taskRepository.findById("non-existent")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> taskService.getTaskById("non-existent"));
        verify(taskRepository, times(1)).findById("non-existent");
    }

    @Test
    @DisplayName("Should throw ValidationException when task ID is blank")
    void testGetTaskWithBlankId() {
        assertThrows(ValidationException.class,
                () -> taskService.getTaskById(""));
        verify(taskRepository, never()).findById(any());
    }

    // ========== UPDATE TASK TESTS ==========

    @Test
    @DisplayName("Should update task successfully with partial update")
    void testUpdateTaskSuccess() {
        TaskUpdateRequest updateRequest = TaskUpdateRequest.builder()
                .title("Updated Task")
                .status(TaskStatus.IN_PROGRESS)
                .build();

        when(taskRepository.findById("task-1")).thenReturn(Optional.of(mockTask));
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        TaskDTO result = taskService.updateTask("task-1", updateRequest);

        assertNotNull(result);
        verify(taskRepository, times(1)).findById("task-1");
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when updating non-existent task")
    void testUpdateNonExistentTask() {
        TaskUpdateRequest updateRequest = TaskUpdateRequest.builder()
                .title("Updated Task")
                .build();

        when(taskRepository.findById("non-existent")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> taskService.updateTask("non-existent", updateRequest));
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Should throw ValidationException when updating with past due date")
    void testUpdateTaskWithPastDueDate() {
        TaskUpdateRequest updateRequest = TaskUpdateRequest.builder()
                .dueDate(LocalDate.now().minusDays(1))
                .build();

        when(taskRepository.findById("task-1")).thenReturn(Optional.of(mockTask));

        assertThrows(ValidationException.class,
                () -> taskService.updateTask("task-1", updateRequest));
        verify(taskRepository, never()).save(any(Task.class));
    }

    // ========== DELETE TASK TESTS ==========

    @Test
    @DisplayName("Should delete task successfully")
    void testDeleteTaskSuccess() {
        when(taskRepository.existsById("task-1")).thenReturn(true);
        when(taskRepository.deleteById("task-1")).thenReturn(true);

        taskService.deleteTask("task-1");

        verify(taskRepository, times(1)).existsById("task-1");
        verify(taskRepository, times(1)).deleteById("task-1");
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting non-existent task")
    void testDeleteNonExistentTask() {
        when(taskRepository.existsById("non-existent")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> taskService.deleteTask("non-existent"));
        verify(taskRepository, never()).deleteById(any());
    }

    // ========== PAGINATION TESTS ==========

    @Test
    @DisplayName("Should throw ValidationException with negative page number")
    void testGetAllTasksWithNegativePage() {
        assertThrows(ValidationException.class,
                () -> taskService.getAllTasks(-1, 10, null));
        verify(taskRepository, never()).findAll();
    }

    @Test
    @DisplayName("Should throw ValidationException with invalid page size")
    void testGetAllTasksWithInvalidPageSize() {
        assertThrows(ValidationException.class,
                () -> taskService.getAllTasks(0, 0, null));

        assertThrows(ValidationException.class,
                () -> taskService.getAllTasks(0, 101, null));
    }
}
