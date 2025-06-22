package os.org.taskflow.developer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import os.org.taskflow.common.ApiResponseEntity;
import os.org.taskflow.developer.entity.Developer;
import os.org.taskflow.developer.service.DeveloperService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperController {
    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping
    public ApiResponseEntity<Optional<List<Developer>>> developers() {
        try{
            return new ApiResponseEntity(
                    Instant.now(),
                    true,
                    "Developers fetched successfully",
                    HttpStatus.OK,
                    this.developerService.developers()
            );
        }catch (Exception ex){
            return new ApiResponseEntity(
                    Instant.now(),
                    true,
                    "Error : " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }
}
