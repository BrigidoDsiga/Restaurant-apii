package com.example.restaurant.repository;

import com.example.restaurant.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    /**
     * Busca pratos por categoria exata.
     * 
     * @param category Categoria do prato
     * @return Lista de pratos da categoria
     */
    List<Dish> findByCategory(String category);

    /**
     * Busca pratos cujo nome contenha o termo informado, ignorando maiúsculas/minúsculas.
     * 
     * @param name Termo para busca no nome
     * @return Lista de pratos que contenham o termo no nome
     */
    List<Dish> findByNameContainingIgnoreCase(String name);

    /**
     * Busca pratos por disponibilidade.
     * 
     * @param disponivel Flag de disponibilidade (true = disponível)
     * @return Lista de pratos disponíveis ou não disponíveis
     */
    List<Dish> findByDisponivel(boolean disponivel);

    /**
     * Busca pratos por categoria e disponibilidade.
     * 
     * @param category Categoria do prato
     * @param disponivel Disponibilidade do prato
     * @return Lista de pratos que correspondam à categoria e disponibilidade
     */
    List<Dish> findByCategoryAndDisponivel(String category, boolean disponivel);
}
