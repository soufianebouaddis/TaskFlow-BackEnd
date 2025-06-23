package os.org.taskflow.manager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import os.org.taskflow.manager.service.ManagerService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ManagerControllerTest {

    @Mock
    private ManagerService managerService;

    @InjectMocks
    private ManagerController managerController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(managerController).build();
    }

    @Test
    void assignedTaskToDeveloper_ValidRequest_ReturnsSuccess() throws Exception {
        Long taskId = 1L;
        UUID developerId = UUID.randomUUID();

        doNothing().when(managerService).assignedTaskToDeveloper(eq(taskId), eq(developerId));

        mockMvc.perform(post("/api/v1/manager/{taskId}/{developerId}", taskId, developerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Task assigned to developer successfully"));

        verify(managerService, times(1)).assignedTaskToDeveloper(eq(taskId), eq(developerId));
    }

    @Test
    void addDeveloperToTeam_ValidRequest_ReturnsSuccess() throws Exception {
        UUID managerId = UUID.randomUUID();
        UUID developerId = UUID.randomUUID();

        doNothing().when(managerService).addDeveloperToTeam(eq(managerId), eq(developerId));

        mockMvc.perform(post("/api/v1/manager/team/{managerId}/{developerId}", managerId, developerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Developer added to team successfully"));

        verify(managerService, times(1)).addDeveloperToTeam(eq(managerId), eq(developerId));
    }
} 