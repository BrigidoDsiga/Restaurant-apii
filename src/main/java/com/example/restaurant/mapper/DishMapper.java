package com.example.restaurant.mapper;

import com.example.restaurant.dto.DishDTO;
import com.example.restaurant.model.Dish;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Mapper para conversão entre Dish e DishDTO.
 */
@Component
public class DishMapper {

    /**
     * Converte uma entidade Dish em DishDTO.
     *
     * @param dish entidade Dish
     * @return DTO correspondente ou null se dish for null
     */
    public DishDTO toDTO(Dish dish) {
        return Optional.ofNullable(dish)
                .map(d -> new DishDTO(
                        d.getId(),
                        d.getName(),
                        d.getDescription(),
                        d.getPrice()))
                .orElse(null);
    }

    /**
     * Converte um DishDTO em entidade Dish.
     *
     * @param dto DTO de prato
     * @return entidade Dish correspondente ou null se dto for null
     */
    public Dish toEntity(DishDTO dto) {
        if (dto == null) return null;

        Dish dish = new Dish();
        dish.setId(dto.getId());
        dish.setName(dto.getName());
        dish.setDescription(dto.getDescription());
        dish.setPrice(dto.getPrice());
        return dish;
    }
}
