package com.example.restaurant.mapper;

import com.example.restaurant.dto.ClientDTO;
import com.example.restaurant.model.Client;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Responsável por mapear entre Client e ClientDTO.
 */
@Component
public class ClientMapper {

    /**
     * Converte um Client para ClientDTO.
     *
     * @param client entidade Client
     * @return DTO correspondente
     */
    public ClientDTO toDTO(Client client) {
        return Optional.ofNullable(client)
                .map(c -> new ClientDTO(
                        c.getId(),
                        c.getName(),
                        c.getEmail(),
                        c.getPhone()))
                .orElse(null);
    }

    /**
     * Converte um ClientDTO para Client.
     *
     * @param dto DTO do cliente
     * @return entidade correspondente
     */
    public Client toEntity(ClientDTO dto) {
        if (dto == null) return null;

        Client client = new Client();
        client.setId(dto.getId());
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setPhone(dto.getPhone());

        return client;
    }
}
