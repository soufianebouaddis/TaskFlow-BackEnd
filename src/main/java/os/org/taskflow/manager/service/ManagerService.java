package os.org.taskflow.manager.service;

import os.org.taskflow.task.entity.Task;

import java.util.Optional;
import java.util.UUID;

public interface ManagerService {
    Optional<Task> assignedTaskToDeveloper(Long taskId , UUID developerId);
}
