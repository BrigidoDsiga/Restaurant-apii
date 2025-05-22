package com.example.restaurant.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    @NotNull(message = "ID do pedido não pode ser nulo")
    private Long id;

    @NotNull(message = "ID do cliente é obrigatório")
    private Long clientId;

    @NotEmpty(message = "Lista de pratos não pode estar vazia")
    private List<@NotNull(message = "ID do prato não pode ser nulo") Long> dishIds;

    @NotNull(message = "Total do pedido é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total deve ser maior que zero")
    private Double total;

    @NotNull(message = "Status do pedido é obrigatório")
    @Size(min = 3, max = 50, message = "Status deve ter entre 3 e 50 caracteres")
    private String status;
}
