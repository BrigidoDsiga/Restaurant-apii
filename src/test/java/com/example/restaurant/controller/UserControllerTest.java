// UserControllerTest
package com.example.restaurant.controller;

import com.example.restaurant.model.User;
import com.example.restaurant.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
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
    void shouldReturnAllUsers() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is("admin")))
                .andExpect(jsonPath("$[1].username", is("user")));
    }

    @Test
    void shouldReturnUserById() throws Exception {
        Mockito.when(userService.getUserById(1L)).thenReturn(user1);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("admin")));
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        Mockito.when(userService.getUserById(99L)).thenThrow(new RuntimeException("Usuário não encontrado"));

        mockMvc.perform(get("/api/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateUser() throws Exception {
        Mockito.when(userService.createUser(any(User.class))).thenReturn(user1);

        String userJson = """
            {
                "username": "admin",
                "password": "123456",
                "email": "admin@email.com",
                "role": "ADMIN"
            }
            """;

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("admin")));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        Mockito.when(userService.updateUser(eq(1L), any(User.class))).thenReturn(user1);

        String userJson = """
            {
                "username": "admin",
                "password": "123456",
                "email": "admin@email.com",
                "role": "ADMIN"
            }
            """;

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("admin")));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        Mockito.when(userService.deleteUser(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}