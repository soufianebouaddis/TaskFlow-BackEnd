package os.org.taskflow.auth.dto;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import os.org.taskflow.developer.dto.DeveloperDTO;
import os.org.taskflow.developer.dto.DeveloperDetailsDTO;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Profile {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private DeveloperDetailsDTO developerDetails;
}
