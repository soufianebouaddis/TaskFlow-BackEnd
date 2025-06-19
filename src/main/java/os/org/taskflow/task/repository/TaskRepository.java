package os.org.taskflow.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import os.org.taskflow.task.entity.Task;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
