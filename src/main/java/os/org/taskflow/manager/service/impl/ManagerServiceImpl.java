package os.org.taskflow.manager.service.impl;

import org.springframework.stereotype.Service;
import os.org.taskflow.developer.entity.Developer;
import os.org.taskflow.developer.repository.DeveloperRepository;
import os.org.taskflow.manager.service.ManagerService;
import os.org.taskflow.task.entity.Task;
import os.org.taskflow.task.repository.TaskRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ManagerServiceImpl implements ManagerService {
    private final DeveloperRepository developerRepository;
    private final TaskRepository taskRepository;

    public ManagerServiceImpl(DeveloperRepository developerRepository, TaskRepository taskRepository) {
        this.developerRepository = developerRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Optional<Task> assignedTaskToDeveloper(Long taskId, UUID developerId) {
        Optional<Developer> developer = this.developerRepository.findById(developerId);
        Optional<Task> task = this.taskRepository.findById(taskId);
        if(developer.isPresent() && task.isPresent()){
            developer.get().getTasks().add(task.get());
            task.get().setDeveloper(developer.get());
            this.developerRepository.save(developer.get());
            this.taskRepository.save(task.get());
            return task;
        }
        return Optional.empty();
    }
}
