// DishControllerTest
package com.example.restaurant.controller;

import com.example.restaurant.model.Client;
import com.example.restaurant.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    private Client client1;
    private Client client2;

    @BeforeEach
    void setUp() {
        client1 = Client.builder()
                .id(1L)
                .name("João Silva")
                .email("joao@email.com")
                .phone("11999999999")
                .address("Rua A, 123")
                .build();

        client2 = Client.builder()
                .id(2L)
                .name("Maria Souza")
                .email("maria@email.com")
                .phone("11888888888")
                .address("Rua B, 456")
                .build();
    }

    @Test
    void shouldReturnAllClients() throws Exception {
        Mockito.when(clientService.getAllClients()).thenReturn(Arrays.asList(client1, client2));

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("João Silva")))
                .andExpect(jsonPath("$[1].name", is("Maria Souza")));
    }

    @Test
    void shouldReturnClientById() throws Exception {
        Mockito.when(clientService.getClientById(1L)).thenReturn(client1);

        mockMvc.perform(get("/api/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("João Silva")));
    }

    @Test
    void shouldReturnNotFoundWhenClientDoesNotExist() throws Exception {
        Mockito.when(clientService.getClientById(99L)).thenThrow(new RuntimeException("Cliente não encontrado"));

        mockMvc.perform(get("/api/clients/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateClient() throws Exception {
        Mockito.when(clientService.createClient(any(Client.class))).thenReturn(client1);

        String clientJson = """
            {
                "name": "João Silva",
                "email": "joao@email.com",
                "phone": "11999999999",
                "address": "Rua A, 123"
            }
            """;

        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("João Silva")));
    }

    @Test
    void shouldUpdateClient() throws Exception {
        Mockito.when(clientService.updateClient(eq(1L), any(Client.class))).thenReturn(client1);

        String clientJson = """
            {
                "name": "João Silva",
                "email": "joao@email.com",
                "phone": "11999999999",
                "address": "Rua A, 123"
            }
            """;

        mockMvc.perform(put("/api/clients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("João Silva")));
    }

    @Test
    void shouldDeleteClient() throws Exception {
        Mockito.when(clientService.deleteClient(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/clients/1"))
                .andExpect(status().isNoContent());
    }
}