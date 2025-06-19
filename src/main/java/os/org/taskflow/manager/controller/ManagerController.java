package os.org.taskflow.manager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import os.org.taskflow.common.ApiResponseEntity;
import os.org.taskflow.manager.service.ManagerService;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/manager")
public class ManagerController {
    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping("/{taskId}/{developerId}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ApiResponseEntity<?> assignedTaskToDeveloper(@PathVariable Long taskId, @PathVariable UUID developerId){
        try{
            return new ApiResponseEntity(
                    Instant.now(),
                    true,
                    "Task assigned to developer successfully",
                    HttpStatus.OK,
                    managerService.assignedTaskToDeveloper(taskId,developerId)
            );
        }catch (Exception ex){
            return new ApiResponseEntity<>(
                    Instant.now(),
                    false,
                    "Error : "+ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }
}
