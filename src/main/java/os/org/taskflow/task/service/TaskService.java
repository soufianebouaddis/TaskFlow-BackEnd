package os.org.taskflow.task.service;

import os.org.taskflow.task.dto.TaskDTO;
import os.org.taskflow.task.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Optional<Task> create(TaskDTO taskDTO);
    Optional<Task> update(Long id,Task Task);
    List<Task> findAll();
    Optional<Task> delete(Long id);
}
