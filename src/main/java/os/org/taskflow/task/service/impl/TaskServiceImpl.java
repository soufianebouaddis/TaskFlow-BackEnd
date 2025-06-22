package os.org.taskflow.task.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import os.org.taskflow.task.dto.TaskDTO;
import os.org.taskflow.task.entity.Task;
import os.org.taskflow.task.repository.TaskRepository;
import os.org.taskflow.task.service.TaskService;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public Optional<Task> create(TaskDTO taskDTO) {
        Task presistTask = new Task();
        presistTask.setTaskLabel(taskDTO.getTaskLabel());
        presistTask.setTaskState(taskDTO.getTaskState());
        presistTask.setCreatedAt(Instant.now());
        return Optional.of(taskRepository.save(presistTask));
    }

    @Override
    public Optional<Task> update(Long id, Task Task) {
        return Optional.ofNullable(taskRepository.findById(id).map((it) -> {
            it.setTaskLabel(Task.getTaskLabel());
            it.setTaskState(Task.getTaskState());
            it.setUpdateAt(Instant.now());
            return taskRepository.save(it);
        }).orElseThrow(
                () -> new EntityNotFoundException("Task Not Found with id : " + id)
        ));
    }

    @Override
    public List<Task> findAll() {
        if(taskRepository.findAll().isEmpty()){
            return Collections.EMPTY_LIST;
        }
        return taskRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Optional<Task> deletedTask = taskRepository.findById(id);
        if(deletedTask.isPresent()){
            taskRepository.deleteById(id);
        }
    }
}
