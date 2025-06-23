package os.org.taskflow.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import os.org.taskflow.task.dto.TaskDTO;
import os.org.taskflow.task.entity.Task;
import os.org.taskflow.task.entity.TaskState;
import os.org.taskflow.task.service.TaskService;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void addNewTask_ValidRequest_ReturnsSuccess() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskLabel("Implement User Authentication");
        taskDTO.setTaskState(TaskState.TODO);

        Task createdTask = new Task();
        createdTask.setId(1L);
        createdTask.setTaskLabel("Implement User Authentication");
        createdTask.setTaskState(TaskState.TODO);
        createdTask.setCreatedAt(Instant.now());

        when(taskService.create(any(TaskDTO.class))).thenReturn(Optional.of(createdTask));

        mockMvc.perform(post("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.taskLabel").value("Implement User Authentication"))
                .andExpect(jsonPath("$.data.taskState").value("TODO"));

        verify(taskService, times(1)).create(any(TaskDTO.class));
    }

    @Test
    void findAllTasks_ValidRequest_ReturnsSuccess() throws Exception {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTaskLabel("Task 1");
        task1.setTaskState(TaskState.TODO);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTaskLabel("Task 2");
        task2.setTaskState(TaskState.IN_PROGRESS);

        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskService.findAll()).thenReturn(tasks);

        mockMvc.perform(get("/api/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Tasks retrieved successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].taskLabel").value("Task 1"))
                .andExpect(jsonPath("$.data[1].taskLabel").value("Task 2"));

        verify(taskService, times(1)).findAll();
    }

    @Test
    void updateTask_ValidRequest_ReturnsSuccess() throws Exception {
        Long taskId = 1L;
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskLabel("Updated Task Label");
        taskDTO.setTaskState(TaskState.DONE);

        Task updatedTask = new Task();
        updatedTask.setId(taskId);
        updatedTask.setTaskLabel("Updated Task Label");
        updatedTask.setTaskState(TaskState.DONE);

        when(taskService.update(eq(taskId), any(TaskDTO.class))).thenReturn(Optional.of(updatedTask));

        mockMvc.perform(put("/api/v1/tasks/{id}", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Task updated successfully"))
                .andExpect(jsonPath("$.data.taskLabel").value("Updated Task Label"))
                .andExpect(jsonPath("$.data.taskState").value("DONE"));

        verify(taskService, times(1)).update(eq(taskId), any(TaskDTO.class));
    }

    @Test
    void deleteTask_ValidRequest_ReturnsSuccess() throws Exception {
        Long taskId = 1L;
        doNothing().when(taskService).delete(taskId);

        mockMvc.perform(delete("/api/v1/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Task deleted successfully"));

        verify(taskService, times(1)).delete(taskId);
    }
} 