package os.org.taskflow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import os.org.taskflow.model.enums.TaskState;

import java.time.Instant;
import java.util.UUID;
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
    private String taskLibelle;
    @Enumerated(EnumType.STRING)
    private TaskState taskState;
    private Instant createdAt;
    private Instant updateAt;
    @ManyToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;
}
