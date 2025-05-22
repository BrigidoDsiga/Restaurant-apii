package com.example.restaurant.mapper;

import com.example.restaurant.dto.ClientDTO;
import com.example.restaurant.model.Client;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ClientMapper {

    public ClientDTO toDTO(Client client) {
        if (Objects.isNull(client)) {
            return null;
        }
        return new ClientDTO(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getPhone()
        );
    }

    public Client toEntity(ClientDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        Client client = new Client();
        client.setId(dto.getId());
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setPhone(dto.getPhone());
        return client;
    }
}
