package os.org.taskflow.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import os.org.taskflow.auth.dto.LoginRequest;
import os.org.taskflow.auth.dto.Profile;
import os.org.taskflow.auth.dto.RegisterRequest;
import os.org.taskflow.auth.dto.UpdateRequest;
import os.org.taskflow.auth.service.UserService;
import os.org.taskflow.common.ApiResponseEntity;
import os.org.taskflow.constant.Constant;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    private final UserService userService;
    private final SecurityContextLogoutHandler logoutHandler;
    public AuthController(UserService userService, SecurityContextLogoutHandler logoutHandler) {
        this.userService = userService;
        this.logoutHandler = logoutHandler;
    }

    @PostMapping("register")
    public ApiResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest){
        try{
            return new ApiResponseEntity(
                    Instant.now(),
                    true,
                    userService.register(registerRequest),
                    HttpStatus.CREATED
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
    @PostMapping("login")
    public ApiResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response){
        Map<String, ResponseCookie> cookieMap = userService.authenticate(loginRequest);
        response.addHeader(HttpHeaders.SET_COOKIE, cookieMap.get(Constant.ACCESS_TOKEN).toString());
        return new ApiResponseEntity(
                Instant.now(),
                true,
                "Welcome",
                HttpStatus.OK
        );
    }

    @GetMapping("profile")
    public ApiResponseEntity<Profile> profile(){
        return new ApiResponseEntity(
                Instant.now(),
                true,
                userService.profile(),
                HttpStatus.OK
        );
    }

    @PutMapping("{id}")
    public ApiResponseEntity<Void> updateProfile(@PathVariable UUID id, @RequestBody @Valid UpdateRequest updateRequest){
        try{
            this.userService.updateProfile(id,updateRequest);
            return new ApiResponseEntity(
                    Instant.now(),
                    true,
                    "Profile updated successfully",
                    HttpStatus.OK,
                    null
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

    @PostMapping("/logout")
    public ApiResponseEntity<?> logout(Authentication authentication, HttpServletRequest request,
                                       HttpServletResponse response) {
        try {
            this.logoutHandler.logout(request, response, authentication);
            return new ApiResponseEntity(
                    Instant.now(),
                    true,
                    "User Logout successfully",
                    HttpStatus.OK,
                    null
            );
        } catch (Exception ex) {
            return new ApiResponseEntity(
                    Instant.now(),
                    true,
                    "Error : " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }
}
