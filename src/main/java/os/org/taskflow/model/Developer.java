package os.org.taskflow.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import os.org.taskflow.model.enums.DeveloperType;

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
    private Manager manager;


    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

}
