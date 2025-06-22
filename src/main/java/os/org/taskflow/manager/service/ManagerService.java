package os.org.taskflow.manager.service;

import os.org.taskflow.developer.entity.Developer;
import os.org.taskflow.task.entity.Task;

import java.util.Optional;
import java.util.UUID;

public interface ManagerService {
    void assignedTaskToDeveloper(Long taskId , UUID developerId);
    void addDeveloperToTeam(UUID managerId,UUID developerId);
}
