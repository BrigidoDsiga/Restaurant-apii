package com.example.restaurant.dto;

import com.example.restaurant.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferência de dados do usuário.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    /**
     * ID do usuário (opcional na criação).
     */
    private Long id;

    /**
     * Nome de usuário (único e obrigatório).
     */
    @NotBlank(message = "Nome de usuário é obrigatório")
    @Size(min = 3, max = 50, message = "Nome de usuário deve ter entre 3 e 50 caracteres")
    private String username;

    /**
     * Endereço de e-mail do usuário (obrigatório e válido).
     */
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    /**
     * Papel ou função do usuário (ex: ADMIN, CLIENT).
     */
    @NotNull(message = "Papel do usuário é obrigatório")
    private UserRole role;
}
