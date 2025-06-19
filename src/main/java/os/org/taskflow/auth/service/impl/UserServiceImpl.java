package os.org.taskflow.auth.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import os.org.taskflow.auth.dto.Profile;
import os.org.taskflow.auth.entity.Role;
import os.org.taskflow.auth.mapper.UserMapper;
import os.org.taskflow.auth.repository.RoleRepository;
import os.org.taskflow.constant.Constant;
import os.org.taskflow.developer.entity.Developer;
import os.org.taskflow.manager.entity.Manager;
import os.org.taskflow.security.service.JwtService;
import os.org.taskflow.auth.dto.LoginRequest;
import os.org.taskflow.auth.dto.RegisterRequest;
import os.org.taskflow.auth.entity.User;
import os.org.taskflow.auth.repository.UserRepository;
import os.org.taskflow.auth.service.UserService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public Map<String, ResponseCookie> authenticate(LoginRequest authRequestDTO) {
        Authentication authentication = authenticateUser(authRequestDTO);
        if (authentication.isAuthenticated()) {
            Optional<User> user = userRepository.findUserByEmail(authRequestDTO.getEmail());
            if(user.isPresent()){
                userRepository.save(user.get());
                String role = user.get().getRole().getRoleName();
                String accessToken = jwtService.generateToken(user.get().getUsername(), role);
                ResponseCookie accessTokenCookie = createAccessTokenCookie(accessToken);
                Map<String, ResponseCookie> cookies = new HashMap<>();
                cookies.put(Constant.ACCESS_TOKEN, accessTokenCookie);
                return cookies;
            }
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
        return null;
    }

    @Override
    public Optional<User> register(RegisterRequest request) {
        Role role = roleRepository.getRoleByRoleName(request.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        User user;
        Instant now = Instant.now();
        if ("MANAGER".equalsIgnoreCase(request.getRole())) {
            Manager manager = userMapper.toManager(request);
            manager.setDevelopers(new ArrayList<>());
            user = manager;
        } else if ("DEVELOPER".equalsIgnoreCase(request.getRole())) {
            Developer developer = userMapper.toDeveloper(request);
            developer.setDeveloperType(request.getDeveloperType());
            developer.setTasks(new ArrayList<>());
            user = developer;
        } else {
            throw new IllegalArgumentException("Unsupported role: " + request.getRole());
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(now);
        user.setUpdateAt(now);
        user.setRole(role);
        userRepository.save(user);
        return Optional.of(user);
    }


    @Override
    public Optional<Profile> profile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        String usernameFromAccessToken = userDetail.getUsername();
        Optional<User> user = userRepository.findUserByEmail(usernameFromAccessToken);
        if (user.isPresent()) {
            Optional<User> u = user;
            return Optional.ofNullable(userMapper.userToProfile(u.get()));
        }
        return Optional.of(new Profile());
    }

    public Authentication authenticateUser(LoginRequest authRequestDTO) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword()));
    }

    public ResponseCookie createAccessTokenCookie(String accessToken) {
        return ResponseCookie.from(Constant.ACCESS_TOKEN,
                        accessToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(7200)
                .build();
    }
}
