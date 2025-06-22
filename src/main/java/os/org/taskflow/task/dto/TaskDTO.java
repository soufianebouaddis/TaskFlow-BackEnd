package os.org.taskflow.task.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import os.org.taskflow.task.entity.TaskState;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskDTO {
    public Long id;
    @NotBlank(message = "Task Label is required")
    @Size(min = 2, max = 30, message = "Task Label must be between 2 and 30 characters")
    private String taskLabel;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Task State is required")
    private TaskState taskState;
}
