package os.org.taskflow.manager.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import os.org.taskflow.manager.entity.Manager;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID> {
    @EntityGraph(attributePaths = "developers")
    Optional<Manager> findById(UUID id);

    @EntityGraph(attributePaths = "developers")
    List<Manager> findAll();
}
