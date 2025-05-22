// DishServiceTest
package com.example.restaurant.service;

import com.example.restaurant.exception.ResourceNotFoundException;
import com.example.restaurant.model.Dish;
import com.example.restaurant.repository.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishServiceTest {

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private DishService dishService;

    private Dish dish1;
    private Dish dish2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dish1 = Dish.builder()
                .id(1L)
                .name("Pizza Margherita")
                .description("Pizza clássica italiana")
                .price(45.0)
                .category("Pizza")
                .build();

        dish2 = Dish.builder()
                .id(2L)
                .name("Lasanha")
                .description("Lasanha à bolonhesa")
                .price(38.5)
                .category("Massas")
                .build();
    }

    @Test
    void shouldReturnAllDishes() {
        when(dishRepository.findAll()).thenReturn(Arrays.asList(dish1, dish2));
        List<Dish> dishes = dishService.getAllDishes();
        assertEquals(2, dishes.size());
        verify(dishRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnDishById() {
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish1));
        Dish dish = dishService.getDishById(1L);
        assertNotNull(dish);
        assertEquals("Pizza Margherita", dish.getName());
        verify(dishRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowWhenDishNotFound() {
        when(dishRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> dishService.getDishById(99L));
    }

    @Test
    void shouldCreateDish() {
        when(dishRepository.save(dish1)).thenReturn(dish1);
        Dish created = dishService.createDish(dish1);
        assertNotNull(created);
        assertEquals("Pizza Margherita", created.getName());
        verify(dishRepository, times(1)).save(dish1);
    }

    @Test
    void shouldUpdateDish() {
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish1));
        when(dishRepository.save(any(Dish.class))).thenReturn(dish1);

        Dish updatedDetails = Dish.builder()
                .name("Pizza Atualizada")
                .description("Pizza com ingredientes especiais")
                .price(50.0)
                .category("Pizza")
                .build();

        Dish updated = dishService.updateDish(1L, updatedDetails);
        assertNotNull(updated);
        assertEquals("Pizza Atualizada", updated.getName());
        verify(dishRepository, times(1)).save(any(Dish.class));
    }

    @Test
    void shouldDeleteDish() {
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish1));
        doNothing().when(dishRepository).delete(dish1);

        boolean deleted = dishService.deleteDish(1L);
        assertTrue(deleted);
        verify(dishRepository, times(1)).delete(dish1);
    }

    @Test
    void shouldReturnDishesByCategory() {
        when(dishRepository.findByCategory("Pizza")).thenReturn(Arrays.asList(dish1));
        List<Dish> dishes = dishService.getDishesByCategory("Pizza");
        assertEquals(1, dishes.size());
        assertEquals("Pizza Margherita", dishes.get(0).getName());
    }

    @Test
    void shouldSearchDishesByName() {
        when(dishRepository.findByNameContainingIgnoreCase("pizza")).thenReturn(Arrays.asList(dish1));
        List<Dish> dishes = dishService.searchDishesByName("pizza");
        assertEquals(1, dishes.size());
        assertEquals("Pizza Margherita", dishes.get(0).getName());
    }
}