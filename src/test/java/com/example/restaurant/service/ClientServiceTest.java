// ClientServiceTest
package com.example.restaurant.service;

import com.example.restaurant.exception.ResourceNotFoundException;
import com.example.restaurant.model.Client;
import com.example.restaurant.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client1;
    private Client client2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

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
    void shouldReturnAllClients() {
        when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));
        var clients = clientService.getAllClients();
        assertEquals(2, clients.size());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnClientById() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client1));
        var client = clientService.getClientById(1L);
        assertNotNull(client);
        assertEquals("João Silva", client.getName());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowWhenClientNotFound() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> clientService.getClientById(99L));
    }

    @Test
    void shouldCreateClient() {
        when(clientRepository.existsByEmail(client1.getEmail())).thenReturn(false);
        when(clientRepository.save(client1)).thenReturn(client1);

        var created = clientService.createClient(client1);
        assertNotNull(created);
        assertEquals("João Silva", created.getName());
        verify(clientRepository, times(1)).save(client1);
    }

    @Test
    void shouldThrowWhenEmailAlreadyExistsOnCreate() {
        when(clientRepository.existsByEmail(client1.getEmail())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> clientService.createClient(client1));
    }

    @Test
    void shouldUpdateClient() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client1));
        when(clientRepository.save(any(Client.class))).thenReturn(client1);

        Client updatedDetails = Client.builder()
                .name("João Atualizado")
                .email("joao@email.com")
                .phone("11999999999")
                .address("Rua Nova, 321")
                .build();

        var updated = clientService.updateClient(1L, updatedDetails);
        assertNotNull(updated);
        assertEquals("João Atualizado", updated.getName());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void shouldDeleteClient() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client1));
        doNothing().when(clientRepository).delete(client1);

        boolean deleted = clientService.deleteClient(1L);
        assertTrue(deleted);
        verify(clientRepository, times(1)).delete(client1);
    }
}