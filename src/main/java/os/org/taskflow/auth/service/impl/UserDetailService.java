package os.org.taskflow.auth.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import os.org.taskflow.auth.entity.User;
import os.org.taskflow.auth.repository.UserRepository;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String email) {
        User user = userRepository.findUserByEmail(email).get();
        if (user != null) {
            return user;
        }
        throw new EntityNotFoundException("User not found");
    }

}
