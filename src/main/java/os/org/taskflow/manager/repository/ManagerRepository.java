package os.org.taskflow.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import os.org.taskflow.manager.entity.Manager;


import java.util.UUID;
@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID> {
}
