package os.org.taskflow.manager.service.impl;

import org.springframework.stereotype.Service;
import os.org.taskflow.developer.entity.Developer;
import os.org.taskflow.developer.repository.DeveloperRepository;
import os.org.taskflow.manager.entity.Manager;
import os.org.taskflow.manager.repository.ManagerRepository;
import os.org.taskflow.manager.service.ManagerService;
import os.org.taskflow.task.entity.Task;
import os.org.taskflow.task.repository.TaskRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ManagerServiceImpl implements ManagerService {
    private final DeveloperRepository developerRepository;
    private final TaskRepository taskRepository;
    private final ManagerRepository managerRepository;
    public ManagerServiceImpl(DeveloperRepository developerRepository, TaskRepository taskRepository, ManagerRepository managerRepository) {
        this.developerRepository = developerRepository;
        this.taskRepository = taskRepository;
        this.managerRepository = managerRepository;
    }

    @Override
    public void assignedTaskToDeveloper(Long taskId, UUID developerId) {
        Optional<Developer> developer = this.developerRepository.findById(developerId);
        Optional<Task> task = this.taskRepository.findById(taskId);
        if(developer.isPresent() && task.isPresent()){
            developer.get().getTasks().add(task.get());
            task.get().setDeveloper(developer.get());
            this.developerRepository.save(developer.get());
            this.taskRepository.save(task.get());
        }
    }

    @Override
    public void addDeveloperToTeam(UUID managerId,UUID developerId) {
        Optional<Developer> developer = this.developerRepository.findById(developerId);
        Optional<Manager> manager = this.managerRepository.findById(managerId);
        if(developer.isPresent() && manager.isPresent()){
            manager.get().getDevelopers().add(developer.get());
            developer.get().setManager(manager.get());
            this.managerRepository.save(manager.get());
            this.developerRepository.save(developer.get());
        }
    }
}
