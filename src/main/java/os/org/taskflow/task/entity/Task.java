package os.org.taskflow.task.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import os.org.taskflow.developer.entity.Developer;

import java.time.Instant;

@Entity
@Table(name = "t_task")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskLabel;
    @Enumerated(EnumType.STRING)
    private TaskState taskState;
    private Instant createdAt;
    private Instant updateAt;
    @ManyToOne
    @JoinColumn(name = "developer_id")
    @JsonBackReference
    private Developer developer;
}
