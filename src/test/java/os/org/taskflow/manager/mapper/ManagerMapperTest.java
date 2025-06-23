package os.org.taskflow.manager.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import os.org.taskflow.manager.dto.ManagerDTO;
import os.org.taskflow.manager.entity.Manager;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerMapperTest {

    private ManagerMapper managerMapper;
    private UUID managerId;
    private Instant now;

    @BeforeEach
    void setUp() {
        managerMapper = Mappers.getMapper(ManagerMapper.class);
        managerId = UUID.randomUUID();
        now = Instant.now();
    }

    @Test
    void testToManagerDTO_ValidManager_ReturnsDTO() {
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setFirstName("John");
        manager.setLastName("Doe");
        manager.setEmail("john.doe@example.com");
        manager.setCreatedAt(now);

        ManagerDTO dto = managerMapper.toManagerDTO(manager);

        assertNotNull(dto);
    }
} 