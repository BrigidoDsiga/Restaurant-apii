package com.example.restaurant.controller;

import com.example.restaurant.dto.DishDTO;
import com.example.restaurant.service.DishService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
<<<<<<< HEAD
        this.dishService = dishService;
=======
        this.dishService = dishService; 
>>>>>>> a6fcf881dd26237b5959d0182a92ca328a33e49c
    }

    @GetMapping
    public ResponseEntity<List<DishDTO>> getAllDishes() {
        List<DishDTO> dishes = dishService.getAllDishes();
        return ResponseEntity.ok(dishes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishDTO> getDishById(@PathVariable Long id) {
        DishDTO dish = dishService.getDishById(id);
        return dish != null
                ? ResponseEntity.ok(dish)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DishDTO> createDish(@Valid @RequestBody DishDTO dishDTO) {
        DishDTO createdDish = dishService.createDish(dishDTO);
        return ResponseEntity.created(URI.create("/api/dishes/" + createdDish.getId()))
                .body(createdDish);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishDTO> updateDish(@PathVariable Long id, @Valid @RequestBody DishDTO dishDTO) {
        DishDTO updatedDish = dishService.updateDish(id, dishDTO);
        return updatedDish != null
                ? ResponseEntity.ok(updatedDish)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        boolean deleted = dishService.deleteDish(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
