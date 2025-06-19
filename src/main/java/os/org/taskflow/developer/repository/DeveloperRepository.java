package os.org.taskflow.developer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import os.org.taskflow.developer.entity.Developer;


import java.util.UUID;
@Repository
public interface DeveloperRepository extends JpaRepository<Developer, UUID> {
}
