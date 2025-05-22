package com.example.restaurant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    @NotNull(message = "ID não pode ser nulo")
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String name;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Size(min = 8, max = 20, message = "Telefone deve ter entre 8 e 20 caracteres")
    private String phone;
}
/// A classe ClientDTO é um Objeto de Transferência de Dados (DTO) que representa a estrutura de dados de um cliente.
// Ela contém campos para o ID, nome, e-mail e número de telefone do cliente.
// As anotações de validação são usadas para garantir que os dados atendam a certos critérios antes de serem processados.
// A anotação @Data do Lombok gera automaticamente os métodos getters, setters, equals, hashCode e toString para a classe.
