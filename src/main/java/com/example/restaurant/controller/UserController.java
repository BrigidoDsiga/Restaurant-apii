package com.example.restaurant.controller;

import com.example.restaurant.dto.UserDTO;
import com.example.restaurant.exception.ResourceNotFoundException;
import com.example.restaurant.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Controller REST para gerenciar usuários.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Construtor para injeção de dependência do serviço de usuários.
     *
     * @param userService serviço responsável pelas operações de usuários
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retorna a lista de todos os usuários.
     *
     * @return lista de usuários
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Retorna um usuário pelo seu ID.
     *
     * @param id ID do usuário
     * @return usuário encontrado ou 404 se não existir
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<UserDTO> userOpt = userService.getUserById(id);
        return userOpt
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
    }

    /**
     * Cria um novo usuário.
     *
     * @param userDTO dados do usuário a serem criados
     * @return usuário criado com URI no header Location
     */
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        URI location = URI.create(String.format("/api/users/%d", createdUser.getId()));
        return ResponseEntity.created(location).body(createdUser);
    }

    /**
     * Atualiza um usuário existente.
     *
     * @param id      ID do usuário a atualizar
     * @param userDTO dados atualizados do usuário
     * @return usuário atualizado ou 404 se não existir
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        Optional<UserDTO> updatedUserOpt = userService.updateUser(id, userDTO);
        return updatedUserOpt
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
    }

    /**
     * Remove um usuário pelo seu ID.
     *
     * @param id ID do usuário
     * @return resposta 204 se deletado, 404 se não encontrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Usuário não encontrado com id: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}

