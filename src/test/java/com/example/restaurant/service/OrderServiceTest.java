// OrderServiceTest
package com.example.restaurant.service;

import com.example.restaurant.exception.ResourceNotFoundException;
import com.example.restaurant.model.Client;
import com.example.restaurant.model.Dish;
import com.example.restaurant.model.Order;
import com.example.restaurant.repository.ClientRepository;
import com.example.restaurant.repository.DishRepository;
import com.example.restaurant.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private OrderService orderService;

    private Client client;
    private Dish dish1;
    private Dish dish2;
    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        client = Client.builder()
                .id(1L)
                .name("João Silva")
                .email("joao@email.com")
                .phone("11999999999")
                .address("Rua A, 123")
                .build();

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

        order = Order.builder()
                .id(1L)
                .client(client)
                .dishes(new HashSet<>(Arrays.asList(dish1, dish2)))
                .total(83.5)
                .status("PENDENTE")
                .build();
    }

    @Test
    void shouldReturnAllOrders() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));
        List<Order> orders = orderService.getAllOrders();
        assertEquals(1, orders.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnOrderById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Order found = orderService.getOrderById(1L);
        assertNotNull(found);
        assertEquals("PENDENTE", found.getStatus());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowWhenOrderNotFound() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(99L));
    }

    @Test
    void shouldCreateOrder() {
        Order newOrder = Order.builder()
                .client(Client.builder().id(1L).build())
                .dishes(new HashSet<>(Arrays.asList(Dish.builder().id(1L).build(), Dish.builder().id(2L).build())))
                .status("PENDENTE")
                .build();

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish1));
        when(dishRepository.findById(2L)).thenReturn(Optional.of(dish2));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order created = orderService.createOrder(newOrder);
        assertNotNull(created);
        assertEquals(83.5, created.getTotal());
        assertEquals("PENDENTE", created.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void shouldThrowWhenCreateOrderWithInvalidClient() {
        Order newOrder = Order.builder()
                .client(Client.builder().id(99L).build())
                .dishes(new HashSet<>(Arrays.asList(Dish.builder().id(1L).build())))
                .status("PENDENTE")
                .build();

        when(clientRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(newOrder));
    }

    @Test
    void shouldThrowWhenCreateOrderWithNoDishes() {
        Order newOrder = Order.builder()
                .client(Client.builder().id(1L).build())
                .dishes(new HashSet<>())
                .status("PENDENTE")
                .build();

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(newOrder));
    }

    @Test
    void shouldUpdateOrder() {
        Order updateDetails = Order.builder()
                .client(Client.builder().id(1L).build())
                .dishes(new HashSet<>(Arrays.asList(Dish.builder().id(1L).build())))
                .status("FINALIZADO")
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish1));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order updated = orderService.updateOrder(1L, updateDetails);
        assertNotNull(updated);
        assertEquals("FINALIZADO", updated.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void shouldDeleteOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        doNothing().when(orderRepository).delete(order);

        boolean deleted = orderService.deleteOrder(1L);
        assertTrue(deleted);
        verify(orderRepository, times(1)).delete(order);
    }

    @Test
    void shouldReturnOrdersByClientId() {
        when(orderRepository.findByClientId(1L)).thenReturn(Arrays.asList(order));
        List<Order> orders = orderService.getOrdersByClientId(1L);
        assertEquals(1, orders.size());
        verify(orderRepository, times(1)).findByClientId(1L);
    }

    @Test
    void shouldReturnOrdersByStatus() {
        when(orderRepository.findByStatus("PENDENTE")).thenReturn(Arrays.asList(order));
        List<Order> orders = orderService.getOrdersByStatus("PENDENTE");
        assertEquals(1, orders.size());
        verify(orderRepository, times(1)).findByStatus("PENDENTE");
    }
}