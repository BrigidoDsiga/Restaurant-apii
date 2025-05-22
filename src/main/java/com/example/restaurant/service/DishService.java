// DishService
package com.example.restaurant.service;

import com.example.restaurant.exception.ResourceNotFoundException;
import com.example.restaurant.model.Dish;
import com.example.restaurant.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public Dish getDishById(Long id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prato não encontrado com id: " + id));
    }

    public Dish createDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public Dish updateDish(Long id, Dish dishDetails) {
        Dish dish = getDishById(id);
        dish.setName(dishDetails.getName());
        dish.setDescription(dishDetails.getDescription());
        dish.setPrice(dishDetails.getPrice());
        dish.setCategory(dishDetails.getCategory());
        return dishRepository.save(dish);
    }

    public boolean deleteDish(Long id) {
        Dish dish = getDishById(id);
        dishRepository.delete(dish);
        return true;
    }

    public List<Dish> getDishesByCategory(String category) {
        return dishRepository.findByCategory(category);
    }

    public List<Dish> searchDishesByName(String name) {
        return dishRepository.findByNameContainingIgnoreCase(name);
    }
}