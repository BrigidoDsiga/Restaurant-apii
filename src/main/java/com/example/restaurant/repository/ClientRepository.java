package com.example.restaurant.service;

import com.example.restaurant.dto.ClientDTO;
import com.example.restaurant.exception.ResourceNotFoundException;
import com.example.restaurant.mapper.ClientMapper;
import com.example.restaurant.model.Client;
import com.example.restaurant.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ClientDTO> getClientById(Long id) {
        return clientRepository.findById(id)
                .map(clientMapper::toDTO);
    }

    public ClientDTO createClient(ClientDTO clientDTO) {
        // Verifica duplicidade por email
        if (clientRepository.existsByEmail(clientDTO.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado: " + clientDTO.getEmail());
        }
        Client client = clientMapper.toEntity(clientDTO);
        client = clientRepository.save(client);
        return clientMapper.toDTO(client);
    }

    public Optional<ClientDTO> updateClient(Long id, ClientDTO clientDTO) {
        return clientRepository.findById(id).map(existingClient -> {
            existingClient.setName(clientDTO.getName());
            existingClient.setEmail(clientDTO.getEmail());
            existingClient.setPhone(clientDTO.getPhone());
            existingClient.setAddress(clientDTO.getAddress());
            Client updatedClient = clientRepository.save(existingClient);
            return clientMapper.toDTO(updatedClient);
        });
    }

    public boolean deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            return false;
        }
        clientRepository.deleteById(id);
        return true;
    }
}
