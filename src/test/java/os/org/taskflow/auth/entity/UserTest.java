package os.org.taskflow.auth.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;
    private Role role;
    private UUID userId;
    private Instant now;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        now = Instant.now();
        
        role = new Role();
        role.setId(1L);
        role.setRoleName("DEVELOPER");
        
        user = new User();
        user.setId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setCreatedAt(now);
        user.setUpdateAt(now);
        user.setRole(role);
    }

    @Test
    void testUserCreation() {
        // Assert
        assertNotNull(user);
        assertEquals(userId, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdateAt());
        assertEquals(role, user.getRole());
    }

    @Test
    void testGetAuthorities() {
        // Act
        Collection<? extends org.springframework.security.core.GrantedAuthority> authorities = user.getAuthorities();

        // Assert
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("DEVELOPER")));
    }

    @Test
    void testGetUsername() {
        // Act
        String username = user.getUsername();

        // Assert
        assertEquals("john.doe@example.com", username);
    }

    @Test
    void testGetPassword() {
        // Act
        String password = user.getPassword();

        // Assert
        assertEquals("password123", password);
    }

    @Test
    void testIsAccountNonExpired() {
        // Act
        boolean isAccountNonExpired = user.isAccountNonExpired();

        // Assert
        assertTrue(isAccountNonExpired);
    }

    @Test
    void testIsAccountNonLocked() {
        // Act
        boolean isAccountNonLocked = user.isAccountNonLocked();

        // Assert
        assertTrue(isAccountNonLocked);
    }

    @Test
    void testIsCredentialsNonExpired() {
        // Act
        boolean isCredentialsNonExpired = user.isCredentialsNonExpired();

        // Assert
        assertTrue(isCredentialsNonExpired);
    }

    @Test
    void testIsEnabled() {
        // Act
        boolean isEnabled = user.isEnabled();

        // Assert
        assertTrue(isEnabled);
    }

    @Test
    void testUserWithNullRole() {
        // Arrange
        user.setRole(null);

        // Act
        Collection<? extends org.springframework.security.core.GrantedAuthority> authorities = user.getAuthorities();

        // Assert
        assertNotNull(authorities);
        assertEquals(0, authorities.size());
    }

    @Test
    void testUserWithDifferentRole() {
        // Arrange
        Role managerRole = new Role();
        managerRole.setRoleName("MANAGER");
        user.setRole(managerRole);

        // Act
        Collection<? extends org.springframework.security.core.GrantedAuthority> authorities = user.getAuthorities();

        // Assert
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("MANAGER")));
    }

    @Test
    void testUserEquality() {
        // Arrange
        User user2 = new User();
        user2.setId(userId);
        user2.setFirstName("John");
        user2.setLastName("Doe");
        user2.setEmail("john.doe@example.com");

        // Act & Assert
        assertEquals(user.getId(), user2.getId());
        assertEquals(user.getFirstName(), user2.getFirstName());
        assertEquals(user.getLastName(), user2.getLastName());
        assertEquals(user.getEmail(), user2.getEmail());
    }

    @Test
    void testUserInequality() {
        // Arrange
        User user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setEmail("jane.smith@example.com");

        // Act & Assert
        assertNotEquals(user.getId(), user2.getId());
        assertNotEquals(user.getFirstName(), user2.getFirstName());
        assertNotEquals(user.getLastName(), user2.getLastName());
        assertNotEquals(user.getEmail(), user2.getEmail());
    }

    @Test
    void testUserWithEmptyFields() {
        // Arrange
        User emptyUser = new User();

        // Act & Assert
        assertNull(emptyUser.getId());
        assertNull(emptyUser.getFirstName());
        assertNull(emptyUser.getLastName());
        assertNull(emptyUser.getEmail());
        assertNull(emptyUser.getPassword());
        assertNull(emptyUser.getCreatedAt());
        assertNull(emptyUser.getUpdateAt());
        assertNull(emptyUser.getRole());
    }

    @Test
    void testUserSettersAndGetters() {
        // Arrange
        User testUser = new User();
        UUID newId = UUID.randomUUID();
        String newFirstName = "Jane";
        String newLastName = "Smith";
        String newEmail = "jane.smith@example.com";
        String newPassword = "newpassword123";
        Instant newCreatedAt = Instant.now();
        Instant newUpdateAt = Instant.now();
        Role newRole = new Role();
        newRole.setRoleName("MANAGER");

        // Act
        testUser.setId(newId);
        testUser.setFirstName(newFirstName);
        testUser.setLastName(newLastName);
        testUser.setEmail(newEmail);
        testUser.setPassword(newPassword);
        testUser.setCreatedAt(newCreatedAt);
        testUser.setUpdateAt(newUpdateAt);
        testUser.setRole(newRole);

        // Assert
        assertEquals(newId, testUser.getId());
        assertEquals(newFirstName, testUser.getFirstName());
        assertEquals(newLastName, testUser.getLastName());
        assertEquals(newEmail, testUser.getEmail());
        assertEquals(newPassword, testUser.getPassword());
        assertEquals(newCreatedAt, testUser.getCreatedAt());
        assertEquals(newUpdateAt, testUser.getUpdateAt());
        assertEquals(newRole, testUser.getRole());
    }
} 