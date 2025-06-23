package os.org.taskflow.task.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import os.org.taskflow.developer.entity.Developer;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    private Task task;
    private Developer developer;
    private Long taskId;
    private Instant now;

    @BeforeEach
    void setUp() {
        taskId = 1L;
        now = Instant.now();
        
        developer = new Developer();
        developer.setId(UUID.randomUUID());
        developer.setFirstName("John");
        developer.setLastName("Doe");
        developer.setEmail("john.doe@example.com");
        
        task = new Task();
        task.setId(taskId);
        task.setTaskLabel("Implement User Authentication");
        task.setTaskState(TaskState.TODO);
        task.setCreatedAt(now);
        task.setUpdateAt(now);
        task.setDeveloper(developer);
    }

    @Test
    void testTaskCreation() {
        // Assert
        assertNotNull(task);
        assertEquals(taskId, task.getId());
        assertEquals("Implement User Authentication", task.getTaskLabel());
        assertEquals(TaskState.TODO, task.getTaskState());
        assertEquals(now, task.getCreatedAt());
        assertEquals(now, task.getUpdateAt());
        assertEquals(developer, task.getDeveloper());
    }

    @Test
    void testTaskWithDifferentStates() {
        // Test TODO state
        task.setTaskState(TaskState.TODO);
        assertEquals(TaskState.TODO, task.getTaskState());

        // Test IN_PROGRESS state
        task.setTaskState(TaskState.IN_PROGRESS);
        assertEquals(TaskState.IN_PROGRESS, task.getTaskState());

        // Test DONE state
        task.setTaskState(TaskState.DONE);
        assertEquals(TaskState.DONE, task.getTaskState());
    }

    @Test
    void testTaskWithNullDeveloper() {
        // Arrange
        task.setDeveloper(null);

        // Assert
        assertNull(task.getDeveloper());
    }

    @Test
    void testTaskWithDifferentDeveloper() {
        // Arrange
        Developer newDeveloper = new Developer();
        newDeveloper.setId(UUID.randomUUID());
        newDeveloper.setFirstName("Jane");
        newDeveloper.setLastName("Smith");
        newDeveloper.setEmail("jane.smith@example.com");

        // Act
        task.setDeveloper(newDeveloper);

        // Assert
        assertEquals(newDeveloper, task.getDeveloper());
        assertEquals("Jane", task.getDeveloper().getFirstName());
        assertEquals("Smith", task.getDeveloper().getLastName());
        assertEquals("jane.smith@example.com", task.getDeveloper().getEmail());
    }

    @Test
    void testTaskEquality() {
        // Arrange
        Task task2 = new Task();
        task2.setId(taskId);
        task2.setTaskLabel("Implement User Authentication");
        task2.setTaskState(TaskState.TODO);

        // Act & Assert
        assertEquals(task.getId(), task2.getId());
        assertEquals(task.getTaskLabel(), task2.getTaskLabel());
        assertEquals(task.getTaskState(), task2.getTaskState());
    }

    @Test
    void testTaskInequality() {
        // Arrange
        Task task2 = new Task();
        task2.setId(2L);
        task2.setTaskLabel("Different Task");
        task2.setTaskState(TaskState.DONE);

        // Act & Assert
        assertNotEquals(task.getId(), task2.getId());
        assertNotEquals(task.getTaskLabel(), task2.getTaskLabel());
        assertNotEquals(task.getTaskState(), task2.getTaskState());
    }

    @Test
    void testTaskWithEmptyFields() {
        // Arrange
        Task emptyTask = new Task();

        // Act & Assert
        assertNull(emptyTask.getId());
        assertNull(emptyTask.getTaskLabel());
        assertNull(emptyTask.getTaskState());
        assertNull(emptyTask.getCreatedAt());
        assertNull(emptyTask.getUpdateAt());
        assertNull(emptyTask.getDeveloper());
    }

    @Test
    void testTaskSettersAndGetters() {
        // Arrange
        Task testTask = new Task();
        Long newId = 999L;
        String newTaskLabel = "New Task Label";
        TaskState newTaskState = TaskState.IN_PROGRESS;
        Instant newCreatedAt = Instant.now();
        Instant newUpdateAt = Instant.now();
        Developer newDeveloper = new Developer();
        newDeveloper.setId(UUID.randomUUID());

        // Act
        testTask.setId(newId);
        testTask.setTaskLabel(newTaskLabel);
        testTask.setTaskState(newTaskState);
        testTask.setCreatedAt(newCreatedAt);
        testTask.setUpdateAt(newUpdateAt);
        testTask.setDeveloper(newDeveloper);

        // Assert
        assertEquals(newId, testTask.getId());
        assertEquals(newTaskLabel, testTask.getTaskLabel());
        assertEquals(newTaskState, testTask.getTaskState());
        assertEquals(newCreatedAt, testTask.getCreatedAt());
        assertEquals(newUpdateAt, testTask.getUpdateAt());
        assertEquals(newDeveloper, testTask.getDeveloper());
    }

    @Test
    void testTaskStateEnumValues() {
        // Test all enum values
        assertEquals(3, TaskState.values().length);
        assertTrue(contains(TaskState.values(), TaskState.TODO));
        assertTrue(contains(TaskState.values(), TaskState.IN_PROGRESS));
        assertTrue(contains(TaskState.values(), TaskState.DONE));
    }

    @Test
    void testTaskWithLongTaskLabel() {
        // Arrange
        String longTaskLabel = "This is a very long task label that should be handled properly by the system";
        task.setTaskLabel(longTaskLabel);

        // Assert
        assertEquals(longTaskLabel, task.getTaskLabel());
    }

    @Test
    void testTaskWithSpecialCharacters() {
        // Arrange
        String specialTaskLabel = "Task with special chars: @#$%^&*()_+-=[]{}|;':\",./<>?";
        task.setTaskLabel(specialTaskLabel);

        // Assert
        assertEquals(specialTaskLabel, task.getTaskLabel());
    }

    @Test
    void testTaskWithUnicodeCharacters() {
        // Arrange
        String unicodeTaskLabel = "Task with unicode: 你好世界 🌍 🚀";
        task.setTaskLabel(unicodeTaskLabel);

        // Assert
        assertEquals(unicodeTaskLabel, task.getTaskLabel());
    }

    private boolean contains(TaskState[] values, TaskState value) {
        for (TaskState state : values) {
            if (state == value) {
                return true;
            }
        }
        return false;
    }
} 