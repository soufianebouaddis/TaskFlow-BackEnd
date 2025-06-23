package os.org.taskflow.auth.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import os.org.taskflow.auth.dto.Profile;
import os.org.taskflow.auth.dto.RegisterRequest;
import os.org.taskflow.auth.entity.Role;
import os.org.taskflow.auth.entity.User;
import os.org.taskflow.developer.dto.DeveloperDTO;
import os.org.taskflow.developer.entity.Developer;
import os.org.taskflow.developer.entity.DeveloperType;
import os.org.taskflow.manager.entity.Manager;
import os.org.taskflow.task.dto.TaskDTO;
import os.org.taskflow.task.entity.Task;
import os.org.taskflow.task.entity.TaskState;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    private UserMapper userMapper;
    private UUID userId;
    private Instant now;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
        userId = UUID.randomUUID();
        now = Instant.now();
    }

    @Test
    void testToUser_ValidRegisterRequest_ReturnsUser() {
        
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setEmail("john.doe@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setRole("DEVELOPER");
        registerRequest.setDeveloperType(DeveloperType.FRONTEND);

        
        User user = userMapper.toUser(registerRequest);

        
        assertNotNull(user);
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testToDto_ValidUser_ReturnsRegisterRequest() {
        
        User user = new User();
        user.setId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        
        Role role = new Role();
        role.setRoleName("DEVELOPER");
        user.setRole(role);

        
        RegisterRequest registerRequest = userMapper.toDto(user);

        
        assertNotNull(registerRequest);
        assertEquals("John", registerRequest.getFirstName());
        assertEquals("Doe", registerRequest.getLastName());
        assertEquals("john.doe@example.com", registerRequest.getEmail());
        assertEquals("password123", registerRequest.getPassword());
        assertEquals("DEVELOPER", registerRequest.getRole());
    }

    @Test
    void testUserToProfile_ValidUser_ReturnsProfile() {
        
        User user = new User();
        user.setId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        
        Role role = new Role();
        role.setRoleName("DEVELOPER");
        user.setRole(role);

        
        Profile profile = userMapper.userToProfile(user);

        
        assertNotNull(profile);
        assertEquals(userId, profile.getId());
        assertEquals("John", profile.getFirstName());
        assertEquals("Doe", profile.getLastName());
        assertEquals("john.doe@example.com", profile.getEmail());
        assertEquals("password123", profile.getPassword());
        assertEquals("DEVELOPER", profile.getRole());
    }

    @Test
    void testToManager_ValidRegisterRequest_ReturnsManager() {
        
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Jane");
        registerRequest.setLastName("Smith");
        registerRequest.setEmail("jane.smith@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setRole("MANAGER");

        
        Manager manager = userMapper.toManager(registerRequest);

        
        assertNotNull(manager);
        assertEquals("Jane", manager.getFirstName());
        assertEquals("Smith", manager.getLastName());
        assertEquals("jane.smith@example.com", manager.getEmail());
        assertEquals("password123", manager.getPassword());
    }

    @Test
    void testToDeveloper_ValidRegisterRequest_ReturnsDeveloper() {
        
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("Alice");
        registerRequest.setLastName("Johnson");
        registerRequest.setEmail("alice.johnson@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setRole("DEVELOPER");
        registerRequest.setDeveloperType(DeveloperType.BACKEND);

        
        Developer developer = userMapper.toDeveloper(registerRequest);

        
        assertNotNull(developer);
        assertEquals("Alice", developer.getFirstName());
        assertEquals("Johnson", developer.getLastName());
        assertEquals("alice.johnson@example.com", developer.getEmail());
        assertEquals("password123", developer.getPassword());
    }

    @Test
    void testDeveloperToProfile_ValidDeveloper_ReturnsProfile() {
        
        Developer developer = new Developer();
        developer.setId(userId);
        developer.setFirstName("John");
        developer.setLastName("Doe");
        developer.setEmail("john.doe@example.com");
        developer.setPassword("password123");
        developer.setDeveloperType(DeveloperType.FRONTEND);

        Task task1 = new Task();
        task1.setId(1L);
        task1.setTaskLabel("Task 1");
        task1.setTaskState(TaskState.TODO);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTaskLabel("Task 2");
        task2.setTaskState(TaskState.IN_PROGRESS);

        List<Task> tasks = Arrays.asList(task1, task2);
        developer.setTasks(tasks);

        
        Profile profile = userMapper.developerToProfile(developer);

        
        assertNotNull(profile);
        assertEquals(userId, profile.getId());
        assertEquals("John", profile.getFirstName());
        assertEquals("Doe", profile.getLastName());
        assertEquals("john.doe@example.com", profile.getEmail());
        assertEquals("password123", profile.getPassword());
        assertEquals("DEVELOPER", profile.getRole());
        assertNotNull(profile.getDeveloperDetails());
        assertNotNull(profile.getDeveloperDetails().getTasks());
        assertEquals(2, profile.getDeveloperDetails().getTasks().size());
    }

    @Test
    void testManagerToProfile_ValidManager_ReturnsProfile() {
        
        Manager manager = new Manager();
        manager.setId(userId);
        manager.setFirstName("Jane");
        manager.setLastName("Smith");
        manager.setEmail("jane.smith@example.com");
        manager.setPassword("password123");

        Developer dev1 = new Developer();
        dev1.setId(UUID.randomUUID());
        dev1.setFirstName("Dev1");
        dev1.setLastName("Last1");

        Developer dev2 = new Developer();
        dev2.setId(UUID.randomUUID());
        dev2.setFirstName("Dev2");
        dev2.setLastName("Last2");

        List<Developer> developers = Arrays.asList(dev1, dev2);
        manager.setDevelopers(developers);

        
        Profile profile = userMapper.managerToProfile(manager);

        
        assertNotNull(profile);
        assertEquals(userId, profile.getId());
        assertEquals("Jane", profile.getFirstName());
        assertEquals("Smith", profile.getLastName());
        assertEquals("jane.smith@example.com", profile.getEmail());
        assertEquals("password123", profile.getPassword());
        assertEquals("MANAGER", profile.getRole());
        assertNotNull(profile.getDeveloperDetails());
        assertNotNull(profile.getDeveloperDetails().getTeam());
        assertEquals(2, profile.getDeveloperDetails().getTeam().size());
    }

    @Test
    void testToTaskDTO_ValidTask_ReturnsTaskDTO() {
        
        Task task = new Task();
        task.setId(1L);
        task.setTaskLabel("Test Task");
        task.setTaskState(TaskState.TODO);

        
        TaskDTO taskDTO = userMapper.toTaskDTO(task);

        
        assertNotNull(taskDTO);
        assertEquals(1L, taskDTO.getId());
        assertEquals("Test Task", taskDTO.getTaskLabel());
        assertEquals(TaskState.TODO, taskDTO.getTaskState());
    }

    @Test
    void testToTaskDTOs_ValidTaskList_ReturnsTaskDTOList() {
        
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTaskLabel("Task 1");
        task1.setTaskState(TaskState.TODO);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTaskLabel("Task 2");
        task2.setTaskState(TaskState.IN_PROGRESS);

        List<Task> tasks = Arrays.asList(task1, task2);

        
        List<TaskDTO> taskDTOs = userMapper.toTaskDTOs(tasks);

        
        assertNotNull(taskDTOs);
        assertEquals(2, taskDTOs.size());
        assertEquals(1L, taskDTOs.get(0).getId());
        assertEquals("Task 1", taskDTOs.get(0).getTaskLabel());
        assertEquals(TaskState.TODO, taskDTOs.get(0).getTaskState());
        assertEquals(2L, taskDTOs.get(1).getId());
        assertEquals("Task 2", taskDTOs.get(1).getTaskLabel());
        assertEquals(TaskState.IN_PROGRESS, taskDTOs.get(1).getTaskState());
    }

    @Test
    void testToDeveloperDTO_ValidDeveloper_ReturnsDeveloperDTO() {
        
        Developer developer = new Developer();
        developer.setId(userId);
        developer.setFirstName("John");
        developer.setLastName("Doe");
        developer.setEmail("john.doe@example.com");
        developer.setDeveloperType(DeveloperType.FRONTEND);

        Task task = new Task();
        task.setId(1L);
        task.setTaskLabel("Test Task");
        task.setTaskState(TaskState.TODO);

        List<Task> tasks = Arrays.asList(task);
        developer.setTasks(tasks);

        
        DeveloperDTO developerDTO = userMapper.toDeveloperDTO(developer);

        
        assertNotNull(developerDTO);
        assertEquals("John", developerDTO.getFirstName());
        assertEquals("Doe", developerDTO.getLastName());
        assertEquals("john.doe@example.com", developerDTO.getEmail());
        assertEquals(DeveloperType.FRONTEND, developerDTO.getDeveloperType());
        assertNotNull(developerDTO.getTasks());
        assertEquals(1, developerDTO.getTasks().size());
    }

    @Test
    void testToDeveloperDTOs_ValidDeveloperList_ReturnsDeveloperDTOList() {
        
        Developer dev1 = new Developer();
        dev1.setId(UUID.randomUUID());
        dev1.setFirstName("Dev1");
        dev1.setLastName("Last1");
        dev1.setEmail("dev1@example.com");
        dev1.setDeveloperType(DeveloperType.FRONTEND);

        Developer dev2 = new Developer();
        dev2.setId(UUID.randomUUID());
        dev2.setFirstName("Dev2");
        dev2.setLastName("Last2");
        dev2.setEmail("dev2@example.com");
        dev2.setDeveloperType(DeveloperType.BACKEND);

        List<Developer> developers = Arrays.asList(dev1, dev2);

        
        List<DeveloperDTO> developerDTOs = userMapper.toDeveloperDTOs(developers);

        
        assertNotNull(developerDTOs);
        assertEquals(2, developerDTOs.size());
        assertEquals("Dev1", developerDTOs.get(0).getFirstName());
        assertEquals("Last1", developerDTOs.get(0).getLastName());
        assertEquals("dev1@example.com", developerDTOs.get(0).getEmail());
        assertEquals(DeveloperType.FRONTEND, developerDTOs.get(0).getDeveloperType());
        assertEquals("Dev2", developerDTOs.get(1).getFirstName());
        assertEquals("Last2", developerDTOs.get(1).getLastName());
        assertEquals("dev2@example.com", developerDTOs.get(1).getEmail());
        assertEquals(DeveloperType.BACKEND, developerDTOs.get(1).getDeveloperType());
    }

    @Test
    void testToTaskDTOs_EmptyList_ReturnsEmptyList() {
        
        List<Task> emptyTasks = Arrays.asList();

        
        List<TaskDTO> taskDTOs = userMapper.toTaskDTOs(emptyTasks);

        
        assertNotNull(taskDTOs);
        assertEquals(0, taskDTOs.size());
    }

    @Test
    void testToDeveloperDTOs_EmptyList_ReturnsEmptyList() {
        
        List<Developer> emptyDevelopers = Arrays.asList();

        
        List<DeveloperDTO> developerDTOs = userMapper.toDeveloperDTOs(emptyDevelopers);

        
        assertNotNull(developerDTOs);
        assertEquals(0, developerDTOs.size());
    }
} 