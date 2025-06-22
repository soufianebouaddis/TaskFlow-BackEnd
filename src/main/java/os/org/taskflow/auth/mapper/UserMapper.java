package os.org.taskflow.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import os.org.taskflow.auth.dto.Profile;
import os.org.taskflow.auth.dto.RegisterRequest;
import os.org.taskflow.auth.entity.User;
import os.org.taskflow.developer.dto.DeveloperDTO;
import os.org.taskflow.developer.entity.Developer;
import os.org.taskflow.manager.entity.Manager;
import os.org.taskflow.task.dto.TaskDTO;
import os.org.taskflow.task.entity.Task;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "role",ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toUser(RegisterRequest registerRequest);

    @Mapping(target = "role", expression = "java(user.getRole().getRoleName())")
    RegisterRequest toDto(User user);

    @Mappings({
            @Mapping(target = "id",source="id"),
            @Mapping(target = "firstName", source = "firstName"),
            @Mapping(target = "lastName", source = "lastName"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "role", expression = "java(user.getRole().getRoleName())")
    })
    Profile userToProfile(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "developers", ignore = true)
    Manager toManager(RegisterRequest request);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "manager", ignore = true)
    Developer toDeveloper(RegisterRequest request);


    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "firstName", source = "firstName"),
            @Mapping(target = "lastName", source = "lastName"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "role", expression = "java(\"DEVELOPER\")"),
            @Mapping(target = "developerDetails.tasks", source = "tasks"),
            @Mapping(target = "developerDetails.team", ignore = true)
    })
    Profile developerToProfile(Developer developer);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "firstName", source = "firstName"),
            @Mapping(target = "lastName", source = "lastName"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "role", expression = "java(\"MANAGER\")"),
            @Mapping(target = "developerDetails.team", source = "developers"),
            @Mapping(target = "developerDetails.tasks", ignore = true)
    })
    Profile managerToProfile(Manager manager);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "taskLabel", source = "taskLabel")
    @Mapping(target = "taskState", source = "taskState")
    TaskDTO toTaskDTO(Task task);
    List<TaskDTO> toTaskDTOs(List<Task> tasks);

    // Mapping each Developer to DeveloperDTO
    @Mappings({
            @Mapping(target = "firstName", source = "firstName"),
            @Mapping(target = "lastName", source = "lastName"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "developerType", source = "developerType"),
            @Mapping(target = "tasks", source = "tasks")
    })
    DeveloperDTO toDeveloperDTO(Developer developer);
    List<DeveloperDTO> toDeveloperDTOs(List<Developer> developers);
}
