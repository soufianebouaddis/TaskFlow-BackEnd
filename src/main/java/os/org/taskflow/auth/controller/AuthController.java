package os.org.taskflow.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;
import os.org.taskflow.auth.dto.LoginRequest;
import os.org.taskflow.auth.dto.Profile;
import os.org.taskflow.auth.dto.RegisterRequest;
import os.org.taskflow.auth.service.UserService;
import os.org.taskflow.common.ApiResponseEntity;
import os.org.taskflow.constant.Constant;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ApiResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest){
        return new ApiResponseEntity(
                Instant.now(),
                true,
                userService.register(registerRequest),
                HttpStatus.CREATED
                );
    }
    @PostMapping("login")
    public ApiResponseEntity<?> register(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response){
        Map<String, ResponseCookie> cookieMap = userService.authenticate(loginRequest);
        response.addHeader(HttpHeaders.SET_COOKIE, cookieMap.get(Constant.ACCESS_TOKEN).toString());

        return new ApiResponseEntity(
                Instant.now(),
                true,
                "Welcome",
                HttpStatus.CREATED
        );
    }

    @GetMapping("profile")
    public ApiResponseEntity<Profile> profile(){
        return new ApiResponseEntity(
                Instant.now(),
                true,
                userService.profile(),
                HttpStatus.CREATED
        );
    }
}
