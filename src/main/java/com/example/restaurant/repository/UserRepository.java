package com.example.restaurant.repository;

import com.example.restaurant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca usuário pelo username.
     * @param username nome de usuário
     * @return Optional com usuário caso exista
     */
    Optional<User> findByUsername(String username);

    /**
     * Busca usuário pelo email.
     * @param email email do usuário
     * @return Optional com usuário caso exista
     */
    Optional<User> findByEmail(String email);

    /**
     * Verifica se existe usuário com determinado username.
     * @param username nome de usuário
     * @return true se existir usuário com username informado
     */
    boolean existsByUsername(String username);

    /**
     * Verifica se existe usuário com determinado email.
     * @param email email do usuário
     * @return true se existir usuário com email informado
     */
    boolean existsByEmail(String email);

    /**
     * Exemplo: busca todos usuários habilitados.
     * Pode ser útil para filtrar só usuários ativos.
     * @param enabled status do usuário
     * @return lista de usuários
     */
    List<User> findByEnabled(boolean enabled);
}
