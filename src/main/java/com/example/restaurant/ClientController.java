package com.example.restaurant.controller;

import com.example.restaurant.dto.ClientDTO;
import com.example.restaurant.exception.ResourceNotFoundException;
import com.example.restaurant.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para gerenciar clientes.
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    /**
     * Construtor para injeção de dependência do serviço de cliente.
     *
     * @param clientService serviço responsável pelas operações de cliente
     */
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Retorna a lista de todos os clientes.
     *
     * @return lista de clientes
     */
    @Operation(summary = "Lista todos os clientes")
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    /**
     * Retorna um cliente pelo seu ID.
     *
     * @param id ID do cliente
     * @return cliente encontrado ou 404 se não existir
     */
    @Operation(summary = "Busca um cliente pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        Optional<ClientDTO> clientOpt = clientService.getClientById(id);
        return clientOpt
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));
    }

    /**
     * Cria um novo cliente.
     *
     * @param clientDTO dados do cliente a serem criados
     * @return cliente criado com URI no header Location
     */
    @Operation(summary = "Cria um novo cliente")
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        ClientDTO createdClient = clientService.createClient(clientDTO);
        URI location = URI.create(String.format("/api/clients/%d", createdClient.getId()));
        return ResponseEntity.created(location).body(createdClient);
    }

    /**
     * Atualiza um cliente existente.
     *
     * @param id        ID do cliente a atualizar
     * @param clientDTO dados atualizados do cliente
     * @return cliente atualizado ou 404 se não existir
     */
    @Operation(summary = "Atualiza um cliente existente")
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @Valid @RequestBody ClientDTO clientDTO) {
        Optional<ClientDTO> updatedClientOpt = clientService.updateClient(id, clientDTO);
        return updatedClientOpt
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));
    }

    /**
     * Remove um cliente pelo seu ID.
     *
     * @param id ID do cliente
     * @return resposta 204 se deletado, 404 se não encontrado
     */
    @Operation(summary = "Remove um cliente pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        boolean deleted = clientService.deleteClient(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Cliente não encontrado com id: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
