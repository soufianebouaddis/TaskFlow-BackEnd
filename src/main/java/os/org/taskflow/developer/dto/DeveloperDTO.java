package os.org.taskflow.developer.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import os.org.taskflow.developer.entity.DeveloperType;
import os.org.taskflow.manager.entity.Manager;
import os.org.taskflow.task.dto.TaskDTO;
import os.org.taskflow.task.entity.Task;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeveloperDTO {
    @Enumerated(EnumType.STRING)
    private DeveloperType developerType;
    private List<TaskDTO> tasks = new ArrayList<>();
}
