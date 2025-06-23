package os.org.taskflow.developer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import os.org.taskflow.developer.dto.DeveloperDTO;
import os.org.taskflow.developer.entity.Developer;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DeveloperMapper {
    DeveloperMapper INSTANCE = Mappers.getMapper(DeveloperMapper.class);

    DeveloperDTO toDeveloperDTO(Developer developer);
    List<DeveloperDTO> toDTOs(List<Developer> developers);
}
