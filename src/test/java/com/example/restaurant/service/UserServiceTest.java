// UserServiceTest
package com.example.restaurant.service;

import com.example.restaurant.exception.ResourceNotFoundException;
import com.example.restaurant.model.User;
import com.example.restaurant.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = User.builder()
                .id(1L)
                .username("admin")
                .password("123456")
                .email("admin@email.com")
                .role("ADMIN")
                .enabled(true)
                .build();

        user2 = User.builder()
                .id(2L)
                .username("user")
                .password("654321")
                .email("user@email.com")
                .role("USER")
                .enabled(true)
                .build();
    }

    @Test
    void shouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        var users = userService.getAllUsers();
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        var user = userService.getUserById(1L);
        assertNotNull(user);
        assertEquals("admin", user.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(99L));
    }

    @Test
    void shouldCreateUser() {
        when(userRepository.existsByUsername(user1.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user1.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(user1.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user1);

        var created = userService.createUser(user1);
        assertNotNull(created);
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(user1.getPassword());
    }

    @Test
    void shouldThrowWhenUsernameExistsOnCreate() {
        when(userRepository.existsByUsername(user1.getUsername())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user1));
    }

    @Test
    void shouldThrowWhenEmailExistsOnCreate() {
        when(userRepository.existsByUsername(user1.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user1.getEmail())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user1));
    }

    @Test
    void shouldUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user1);

        User updatedDetails = User.builder()
                .username("admin")
                .password("newpass")
                .email("admin@email.com")
                .role("ADMIN")
                .build();

        var updated = userService.updateUser(1L, updatedDetails);
        assertNotNull(updated);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldDeleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        doNothing().when(userRepository).delete(user1);

        boolean deleted = userService.deleteUser(1L);
        assertTrue(deleted);
        verify(userRepository, times(1)).delete(user1);
    }
}

    @Test
    void shouldReturnUsersByRole() {
        when(userRepository.findByRole("ADMIN")).thenReturn(Arrays.asList(user1));
        var users = userService.getUsersByRole("ADMIN");
        assertEquals(1, users.size());
        verify(userRepository, times(1)).findByRole("ADMIN");
    }
}   