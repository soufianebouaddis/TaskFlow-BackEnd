package os.org.taskflow.developer.service;

import os.org.taskflow.developer.entity.Developer;

import java.util.List;
import java.util.Optional;

public interface DeveloperService {
    Optional<List<Developer>> developers(); //get all developers
}
