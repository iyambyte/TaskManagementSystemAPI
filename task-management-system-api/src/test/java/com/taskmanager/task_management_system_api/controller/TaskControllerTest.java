package com.taskmanager.task_management_system_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanager.task_management_system_api.dto.TaskCreateRequest;
import com.taskmanager.task_management_system_api.dto.TaskDTO;
import com.taskmanager.task_management_system_api.dto.TaskUpdateRequest;
import com.taskmanager.task_management_system_api.enums.TaskStatus;
import com.taskmanager.task_management_system_api.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for TaskController
 */
@WebMvcTest(TaskController.class)
@DisplayName("TaskController Integration Tests")
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private TaskCreateRequest validCreateRequest;
    private TaskDTO mockTaskDTO;

    @BeforeEach
    void setUp() {
        LocalDate futureDate = LocalDate.now().plusDays(7);

        validCreateRequest = TaskCreateRequest.builder()
                .title("Test Task")
                .description("Test Description")
                .dueDate(futureDate)
                .status(TaskStatus.PENDING)
                .build();

        mockTaskDTO = TaskDTO.builder()
                .id("task-1")
                .title("Test Task")
                .description("Test Description")
                .status(TaskStatus.PENDING)
                .dueDate(futureDate)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // ========== POST /tasks TESTS ==========

    @Test
    @DisplayName("Should create task successfully with POST /tasks")
    void testCreateTaskSuccess() throws Exception {
        when(taskService.createTask(any(TaskCreateRequest.class)))
                .thenReturn(mockTaskDTO);

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validCreateRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("task-1"))
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(taskService, times(1)).createTask(any(TaskCreateRequest.class));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when title is missing")
    void testCreateTaskWithoutTitle() throws Exception {
        TaskCreateRequest invalidRequest = TaskCreateRequest.builder()
                .description("Test Description")
                .dueDate(LocalDate.now().plusDays(7))
                .build();

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verify(taskService, never()).createTask(any(TaskCreateRequest.class));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when due date is missing")
    void testCreateTaskWithoutDueDate() throws Exception {
        TaskCreateRequest invalidRequest = TaskCreateRequest.builder()
                .title("Test Task")
                .description("Test Description")
                .build();

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verify(taskService, never()).createTask(any(TaskCreateRequest.class));
    }

    // ========== GET /tasks/{id} TESTS ==========

    @Test
    @DisplayName("Should retrieve task successfully with GET /tasks/{id}")
    void testGetTaskSuccess() throws Exception {
        when(taskService.getTaskById("task-1"))
                .thenReturn(mockTaskDTO);

        mockMvc.perform(get("/tasks/task-1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("task-1"))
                .andExpect(jsonPath("$.title").value("Test Task"));

        verify(taskService, times(1)).getTaskById("task-1");
    }

    @Test
    @DisplayName("Should return 404 Not Found when task doesn't exist")
    void testGetTaskNotFound() throws Exception {
        when(taskService.getTaskById("non-existent"))
                .thenThrow(new com.taskmanager.task_management_system_api.exception.ResourceNotFoundException(
                        "Task not found with id: non-existent"));

        mockMvc.perform(get("/tasks/non-existent")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource Not Found"));

        verify(taskService, times(1)).getTaskById("non-existent");
    }

    // ========== PUT /tasks/{id} TESTS ==========

    @Test
    @DisplayName("Should update task successfully with PUT /tasks/{id}")
    void testUpdateTaskSuccess() throws Exception {
        TaskUpdateRequest updateRequest = TaskUpdateRequest.builder()
                .title("Updated Task")
                .status(TaskStatus.IN_PROGRESS)
                .build();

        TaskDTO updatedDTO = mockTaskDTO;
        updatedDTO.setTitle("Updated Task");
        updatedDTO.setStatus(TaskStatus.IN_PROGRESS);

        when(taskService.updateTask(eq("task-1"), any(TaskUpdateRequest.class)))
                .thenReturn(updatedDTO);

        mockMvc.perform(put("/tasks/task-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("task-1"))
                .andExpect(jsonPath("$.title").value("Updated Task"));

        verify(taskService, times(1)).updateTask(eq("task-1"), any(TaskUpdateRequest.class));
    }

    @Test
    @DisplayName("Should return 404 Not Found when updating non-existent task")
    void testUpdateNonExistentTask() throws Exception {
        TaskUpdateRequest updateRequest = TaskUpdateRequest.builder()
                .title("Updated Task")
                .build();

        when(taskService.updateTask(eq("non-existent"), any(TaskUpdateRequest.class)))
                .thenThrow(new com.taskmanager.task_management_system_api.exception.ResourceNotFoundException(
                        "Task not found with id: non-existent"));

        mockMvc.perform(put("/tasks/non-existent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).updateTask(eq("non-existent"), any(TaskUpdateRequest.class));
    }

    // ========== DELETE /tasks/{id} TESTS ==========

    @Test
    @DisplayName("Should delete task successfully with DELETE /tasks/{id}")
    void testDeleteTaskSuccess() throws Exception {
        doNothing().when(taskService).deleteTask("task-1");

        mockMvc.perform(delete("/tasks/task-1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(taskService, times(1)).deleteTask("task-1");
    }

    @Test
    @DisplayName("Should return 404 Not Found when deleting non-existent task")
    void testDeleteNonExistentTask() throws Exception {
        doThrow(new com.taskmanager.task_management_system_api.exception.ResourceNotFoundException(
                "Task not found with id: non-existent"))
                .when(taskService).deleteTask("non-existent");

        mockMvc.perform(delete("/tasks/non-existent")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).deleteTask("non-existent");
    }

    // ========== GET /tasks TESTS ==========

    @Test
    @DisplayName("Should return 400 Bad Request with invalid pagination parameters")
    void testGetAllTasksInvalidPagination() throws Exception {
        when(taskService.getAllTasks(-1, 10, null))
                .thenThrow(new com.taskmanager.task_management_system_api.exception.ValidationException(
                    "Page number cannot be negative"));

        mockMvc.perform(get("/tasks?page=-1&pageSize=10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should successfully retrieve tasks with filter parameter")
    void testGetAllTasksWithFilter() throws Exception {
        mockMvc.perform(get("/tasks?page=0&pageSize=10&status=PENDING")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(taskService, times(1)).getAllTasks(0, 10, TaskStatus.PENDING);
    }

    // ========== HEALTH CHECK TEST ==========

    @Test
    @DisplayName("Should return health status")
    void testHealthCheck() throws Exception {
        mockMvc.perform(get("/tasks/health")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Task Management API is running!"));
    }
}
