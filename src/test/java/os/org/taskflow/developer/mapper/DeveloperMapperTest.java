package os.org.taskflow.developer.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import os.org.taskflow.developer.dto.DeveloperDTO;
import os.org.taskflow.developer.entity.Developer;
import os.org.taskflow.developer.entity.DeveloperType;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DeveloperMapperTest {

    private DeveloperMapper developerMapper;
    private UUID developerId;
    private Instant now;

    @BeforeEach
    void setUp() {
        developerMapper = Mappers.getMapper(DeveloperMapper.class);
        developerId = UUID.randomUUID();
        now = Instant.now();
    }

    @Test
    void testToDeveloperDTO_ValidDeveloper_ReturnsDTO() {
        Developer developer = new Developer();
        developer.setId(developerId);
        developer.setFirstName("John");
        developer.setLastName("Doe");
        developer.setEmail("john.doe@example.com");
        developer.setDeveloperType(DeveloperType.FRONTEND);
        developer.setCreatedAt(now);

        DeveloperDTO dto = developerMapper.toDeveloperDTO(developer);

        assertNotNull(dto);
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("john.doe@example.com", dto.getEmail());
        assertEquals(DeveloperType.FRONTEND, dto.getDeveloperType());
    }
} 