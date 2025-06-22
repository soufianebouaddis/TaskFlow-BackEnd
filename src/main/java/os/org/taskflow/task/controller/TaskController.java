package os.org.taskflow.task.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import os.org.taskflow.common.ApiResponseEntity;
import os.org.taskflow.task.dto.TaskDTO;
import os.org.taskflow.task.entity.Task;
import os.org.taskflow.task.service.TaskService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    public ApiResponseEntity<Optional<Task>> addNewTask(@RequestBody @Valid TaskDTO taskDTO){
        try{
            return new ApiResponseEntity<>(
                    Instant.now(),
                    true,
                    taskService.create(taskDTO),
                    HttpStatus.CREATED
            );
        }catch (Exception ex){
            return new ApiResponseEntity<>(
                    Instant.now(),
                    false,
                    "Error : "+ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('MANAGER','DEVELOPER')")
    public ApiResponseEntity<List<Task>> findAllTasks(){
        return new ApiResponseEntity<>(
                Instant.now(),
                true,
                "Tasks retrieved successfully",
                HttpStatus.OK,
                taskService.findAll()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER','DEVELOPER')")
    public ApiResponseEntity<Optional<Task>> updateTask(@PathVariable("id")Long id , @RequestBody @Valid Task task){
        return new ApiResponseEntity<>(
                Instant.now(),
                true,
                "Task updated successfully",
                HttpStatus.OK,
                taskService.update(id,task)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ApiResponseEntity<Optional<Task>> deleteTask(@PathVariable("id")Long id){
        try{
            taskService.delete(id);
            return new ApiResponseEntity<>(
                    Instant.now(),
                    true,
                    "Task deleted successfully",
                    HttpStatus.OK,
                    null
                    );
        }catch (Exception ex){
            return new ApiResponseEntity<>(
                    Instant.now(),
                    false,
                    "Error : "+ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }
}
