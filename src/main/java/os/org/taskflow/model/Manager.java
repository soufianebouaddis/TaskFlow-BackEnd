package os.org.taskflow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_manager")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Manager extends User {

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Developer> developers = new ArrayList<>();

}
