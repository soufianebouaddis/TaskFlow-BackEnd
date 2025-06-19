package os.org.taskflow.auth.service;

import org.springframework.http.ResponseCookie;
import os.org.taskflow.auth.dto.LoginRequest;
import os.org.taskflow.auth.dto.Profile;
import os.org.taskflow.auth.dto.RegisterRequest;
import os.org.taskflow.auth.entity.User;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    Map<String, ResponseCookie> authenticate(LoginRequest login);
    Optional<User> register(RegisterRequest registerRequest);
    Optional<Profile> profile();
}
