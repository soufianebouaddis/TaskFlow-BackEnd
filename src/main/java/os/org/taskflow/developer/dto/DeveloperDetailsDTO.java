package os.org.taskflow.developer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import os.org.taskflow.task.dto.TaskDTO;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeveloperDetailsDTO {
    private List<TaskDTO> tasks; // if the role is developer we will display tasks assigned to him
    private List<DeveloperDTO> team; // if the role is manager we will display developers of the team because each manager has his own team
}
