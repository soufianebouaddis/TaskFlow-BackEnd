package os.org.taskflow.developer.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import os.org.taskflow.developer.entity.Developer;
import os.org.taskflow.developer.entity.DeveloperType;
import os.org.taskflow.developer.service.DeveloperService;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DeveloperControllerTest {

    @Mock
    private DeveloperService developerService;

    @InjectMocks
    private DeveloperController developerController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(developerController).build();
    }

    @Test
    void developers_ValidRequest_ReturnsSuccess() throws Exception {
        Developer developer1 = new Developer();
        developer1.setId(UUID.randomUUID());
        developer1.setFirstName("John");
        developer1.setLastName("Doe");
        developer1.setEmail("john.doe@example.com");
        developer1.setDeveloperType(DeveloperType.FRONTEND);

        Developer developer2 = new Developer();
        developer2.setId(UUID.randomUUID());
        developer2.setFirstName("Jane");
        developer2.setLastName("Smith");
        developer2.setEmail("jane.smith@example.com");
        developer2.setDeveloperType(DeveloperType.BACKEND);

        List<Developer> developers = Arrays.asList(developer1, developer2);

        when(developerService.developers()).thenReturn(Optional.of(developers));

        mockMvc.perform(get("/api/v1/developers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Developers fetched successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].firstName").value("John"))
                .andExpect(jsonPath("$.data[0].lastName").value("Doe"))
                .andExpect(jsonPath("$.data[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.data[0].developerType").value("FRONTEND"))
                .andExpect(jsonPath("$.data[1].firstName").value("Jane"))
                .andExpect(jsonPath("$.data[1].lastName").value("Smith"))
                .andExpect(jsonPath("$.data[1].email").value("jane.smith@example.com"))
                .andExpect(jsonPath("$.data[1].developerType").value("BACKEND"));

        verify(developerService, times(1)).developers();
    }

    @Test
    void developers_SingleDeveloper_ReturnsSuccess() throws Exception {
        Developer developer = new Developer();
        developer.setId(UUID.randomUUID());
        developer.setFirstName("Alice");
        developer.setLastName("Johnson");
        developer.setEmail("alice.johnson@example.com");
        developer.setDeveloperType(DeveloperType.BACKEND);
        developer.setCreatedAt(Instant.now());

        List<Developer> developers = Arrays.asList(developer);

        when(developerService.developers()).thenReturn(Optional.of(developers));

        mockMvc.perform(get("/api/v1/developers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Developers fetched successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].firstName").value("Alice"))
                .andExpect(jsonPath("$.data[0].lastName").value("Johnson"))
                .andExpect(jsonPath("$.data[0].email").value("alice.johnson@example.com"))
                .andExpect(jsonPath("$.data[0].developerType").value("BACKEND"));

        verify(developerService, times(1)).developers();
    }

    @Test
    void developers_EmptyList_ReturnsSuccess() throws Exception {
        List<Developer> emptyList = Arrays.asList();
        when(developerService.developers()).thenReturn(Optional.of(emptyList));

        mockMvc.perform(get("/api/v1/developers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Developers fetched successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));

        verify(developerService, times(1)).developers();
    }

    @Test
    void developers_ServiceReturnsEmptyOptional_ReturnsSuccess() throws Exception {
        when(developerService.developers()).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/developers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Developers fetched successfully"))
                .andExpect(jsonPath("$.data").isEmpty());

        verify(developerService, times(1)).developers();
    }
} 