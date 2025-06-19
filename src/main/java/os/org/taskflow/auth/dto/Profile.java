package os.org.taskflow.auth.dto;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
