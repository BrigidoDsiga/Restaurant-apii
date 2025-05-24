package com.example.restaurant.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para representar um pedido realizado no sistema.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    /**
     * ID do pedido (utilizado em atualizações e consultas).
     */
    private Long id;

    /**
     * ID do cliente que realizou o pedido.
     */
    @NotNull(message = "ID do cliente é obrigatório")
    private Long clientId;

    /**
     * Lista de IDs dos pratos incluídos no pedido.
     */
    @NotEmpty(message = "Lista de pratos não pode estar vazia")
    private List<@NotNull(message = "ID do prato não pode ser nulo") Long> dishIds;

    /**
     * Valor total do pedido.
     */
    @NotNull(message = "Total do pedido é obrigatório")
    @DecimalMin(value = "0.01", inclusive = true, message = "Total deve ser maior que zero")
    private Double total;

    /**
     * Status atual do pedido (ex: 'PENDENTE', 'PREPARANDO', 'ENTREGUE').
     */
    @NotNull(message = "Status do pedido é obrigatório")
    @Size(min = 3, max = 50, message = "Status deve ter entre 3 e 50 caracteres")
    private String status;
}
