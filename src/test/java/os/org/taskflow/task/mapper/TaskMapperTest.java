package os.org.taskflow.task.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import os.org.taskflow.task.dto.TaskDTO;
import os.org.taskflow.task.entity.Task;
import os.org.taskflow.task.entity.TaskState;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class TaskMapperTest {

    private TaskMapper taskMapper;
    private Instant now;

    @BeforeEach
    void setUp() {
        taskMapper = Mappers.getMapper(TaskMapper.class);
        now = Instant.now();
    }

    @Test
    void testToTaskDTO_ValidTask_ReturnsDTO() {
        Task task = new Task();
        task.setId(1L);
        task.setTaskLabel("Test Task");
        task.setTaskState(TaskState.TODO);
        task.setCreatedAt(now);

        TaskDTO dto = taskMapper.toTaskDTO(task);

        assertNotNull(dto);
        assertEquals("Test Task", dto.getTaskLabel());
        assertEquals(TaskState.TODO, dto.getTaskState());
    }
} 