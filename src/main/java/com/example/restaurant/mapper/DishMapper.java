package com.example.restaurant.mapper;

import com.example.restaurant.dto.DishDTO;
import com.example.restaurant.model.Dish;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DishMapper {

    public DishDTO toDTO(Dish dish) {
        if (Objects.isNull(dish)) {
            return null;
        }
        return new DishDTO(
                dish.getId(),
                dish.getName(),
                dish.getDescription(),
                dish.getPrice()
        );
    }

    public Dish toEntity(DishDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        Dish dish = new Dish();
        dish.setId(dto.getId());
        dish.setName(dto.getName());
        dish.setDescription(dto.getDescription());
        dish.setPrice(dto.getPrice());
        return dish;
    }
}
