package os.org.taskflow.task.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import os.org.taskflow.task.dto.TaskDTO;
import os.org.taskflow.task.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "taskLabel", source = "taskLabel"),
            @Mapping(target = "taskState", source = "taskState"),
            @Mapping(target = "createdAt",ignore = true),
            @Mapping(target = "updateAt",ignore = true),
            @Mapping(target = "developer", ignore = true)
    })
    Task toEntity(TaskDTO taskDTO);

    TaskDTO toDto(TaskDTO taskDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "taskLabel", source = "taskLabel")
    @Mapping(target = "taskState", source = "taskState")
    TaskDTO toTaskDTO(Task task);
}
