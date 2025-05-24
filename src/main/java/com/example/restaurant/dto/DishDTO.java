package com.example.restaurant.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferência de dados de pratos do cardápio.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDTO {

    /**
     * ID do prato.
     * Não é necessário na criação (POST).
     */
    private Long id;

    /**
     * Nome do prato.
     */
    @NotBlank(message = "Nome do prato é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String name;

    /**
     * Descrição do prato.
     */
    @NotBlank(message = "Descrição do prato é obrigatória")
    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    private String description;

    /**
     * Preço do prato.
     */
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", inclusive = true, message = "Preço deve ser maior que zero")
    private Double price;
}
