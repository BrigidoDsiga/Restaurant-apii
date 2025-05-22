package com.example.restaurant.repository;

import com.example.restaurant.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    /**
     * Busca um cliente pelo email.
     * @param email email do cliente
     * @return Optional com o cliente caso exista
     */
    Optional<Client> findByEmail(String email);

    /**
     * Verifica se um cliente existe pelo email.
     * @param email email para verificação
     * @return true se existir cliente com o email informado
     */
    boolean existsByEmail(String email);

    /**
     * Busca clientes cujo nome contenha o termo informado, ignorando maiúsculas/minúsculas.
     * Útil para filtros e buscas parciais.
     * @param name termo para busca no nome do cliente
     * @return lista de clientes encontrados
     */
    List<Client> findByNameContainingIgnoreCase(String name);
}
