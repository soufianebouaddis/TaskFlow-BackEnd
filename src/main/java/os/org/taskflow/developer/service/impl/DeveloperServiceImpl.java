package os.org.taskflow.developer.service.impl;

import org.springframework.stereotype.Service;
import os.org.taskflow.developer.entity.Developer;
import os.org.taskflow.developer.repository.DeveloperRepository;
import os.org.taskflow.developer.service.DeveloperService;

import java.util.List;
import java.util.Optional;

@Service
public class DeveloperServiceImpl implements DeveloperService {
    private final DeveloperRepository developerRepository;

    public DeveloperServiceImpl(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    @Override
    public Optional<List<Developer>> developers() {
        if(!this.developerRepository.findAll().isEmpty()){
            return Optional.of(this.developerRepository.findAll());
        }
        return Optional.empty();
    }
}
