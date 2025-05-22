package com.example.restaurant.mapper;

import com.example.restaurant.dto.DishDTO;
import com.example.restaurant.model.Dish;
import org.springframework.stereotype.Component;

@Component
public class DishMapper {

    public DishDTO toDTO(Dish dish) {
        if (dish == null) {
            return null;
        }
        return new DishDTO(
                dish.getId(),
                dish.getName(),
                dish.getDescription(),
                dish.getPrice()
        );
    }

    public Dish toEntity(DishDTO dishDTO) {
        if (dishDTO == null) {
            return null;
        }
        Dish dish = new Dish();
        dish.setId(dishDTO.getId());
        dish.setName(dishDTO.getName());
        dish.setDescription(dishDTO.getDescription());
        dish.setPrice(dishDTO.getPrice());
        return dish;
    }
}