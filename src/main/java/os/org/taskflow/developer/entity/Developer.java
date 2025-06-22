package os.org.taskflow.developer.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import os.org.taskflow.manager.entity.Manager;
import os.org.taskflow.task.entity.Task;
import os.org.taskflow.auth.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_developer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Developer extends User {
    @Enumerated(EnumType.STRING)
    private DeveloperType developerType;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    @JsonBackReference
    private Manager manager;
    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Task> tasks = new ArrayList<>();

}
