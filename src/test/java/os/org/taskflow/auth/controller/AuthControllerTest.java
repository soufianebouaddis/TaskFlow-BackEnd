package os.org.taskflow.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import os.org.taskflow.auth.dto.LoginRequest;
import os.org.taskflow.auth.dto.Profile;
import os.org.taskflow.auth.dto.RegisterRequest;
import os.org.taskflow.auth.dto.UpdateRequest;
import os.org.taskflow.auth.entity.User;
import os.org.taskflow.auth.service.UserService;
import os.org.taskflow.constant.Constant;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private SecurityContextLogoutHandler logoutHandler;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void register_ValidRequest_ReturnsSuccess() throws Exception {
        
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setEmail("john.doe@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setRole("DEVELOPER");

        User expectedUser = new User();
        expectedUser.setId(UUID.randomUUID());
        expectedUser.setFirstName("John");
        expectedUser.setLastName("Doe");
        expectedUser.setEmail("john.doe@example.com");

        when(userService.register(any(RegisterRequest.class))).thenReturn(Optional.of(expectedUser));

        
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.firstName").value("John"))
                .andExpect(jsonPath("$.data.lastName").value("Doe"))
                .andExpect(jsonPath("$.data.email").value("john.doe@example.com"));

        verify(userService, times(1)).register(any(RegisterRequest.class));
    }

    @Test
    void login_ValidRequest_ReturnsSuccess() throws Exception {
        
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john.doe@example.com");
        loginRequest.setPassword("password123");

        Map<String, org.springframework.http.ResponseCookie> cookieMap = new HashMap<>();
        org.springframework.http.ResponseCookie accessToken = org.springframework.http.ResponseCookie
                .from(Constant.ACCESS_TOKEN, "test-token")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .build();
        cookieMap.put(Constant.ACCESS_TOKEN, accessToken);

        when(userService.authenticate(any(LoginRequest.class))).thenReturn(cookieMap);

        
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("Welcome"))
                .andExpect(header().exists(HttpHeaders.SET_COOKIE));

        verify(userService, times(1)).authenticate(any(LoginRequest.class));
    }

    @Test
    void profile_ValidRequest_ReturnsUserProfile() throws Exception {
        
        Profile expectedProfile = new Profile();
        expectedProfile.setId(UUID.randomUUID());
        expectedProfile.setFirstName("John");
        expectedProfile.setLastName("Doe");
        expectedProfile.setEmail("john.doe@example.com");
        expectedProfile.setRole("DEVELOPER");

        when(userService.profile()).thenReturn(Optional.of(expectedProfile));

        
        mockMvc.perform(get("/api/v1/auth/profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.firstName").value("John"))
                .andExpect(jsonPath("$.data.lastName").value("Doe"))
                .andExpect(jsonPath("$.data.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.data.role").value("DEVELOPER"));

        verify(userService, times(1)).profile();
    }

    @Test
    void updateProfile_ValidRequest_ReturnsSuccess() throws Exception {
        
        UUID userId = UUID.randomUUID();
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setFirstName("Jane");
        updateRequest.setLastName("Smith");

        doNothing().when(userService).updateProfile(any(UUID.class), any(UpdateRequest.class));

        
        mockMvc.perform(put("/api/v1/auth/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Profile updated successfully"));

        verify(userService, times(1)).updateProfile(eq(userId), any(UpdateRequest.class));
    }

    @Test
    void logout_ValidRequest_ReturnsSuccess() throws Exception {
        
        Authentication authentication = mock(Authentication.class);

        doNothing().when(logoutHandler).logout(any(), any(), any(Authentication.class));

        
        mockMvc.perform(post("/api/v1/auth/logout")
                .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User Logout successfully"));

        verify(logoutHandler, times(1)).logout(any(), any(), eq(authentication));
    }
} 