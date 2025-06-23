package os.org.taskflow.manager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import os.org.taskflow.manager.dto.ManagerDTO;
import os.org.taskflow.manager.entity.Manager;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ManagerMapper {
    ManagerMapper INSTANCE = Mappers.getMapper(ManagerMapper.class);

    ManagerDTO toManagerDTO(Manager manager);
    List<ManagerDTO> toDTOs(List<Manager> managers);
}
