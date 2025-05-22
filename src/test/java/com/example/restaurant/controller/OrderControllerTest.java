// OrderControllerTest
package com.example.restaurant.controller;

import com.example.restaurant.model.Client;
import com.example.restaurant.model.Dish;
import com.example.restaurant.model.Order;
import com.example.restaurant.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private Order order1;
    private Order order2;
    private Client client;
    private Dish dish1;
    private Dish dish2;

    @BeforeEach
    void setUp() {
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

        order1 = Order.builder()
                .id(1L)
                .client(client)
                .dishes(new HashSet<>(Arrays.asList(dish1, dish2)))
                .total(83.5)
                .status("PENDENTE")
                .build();

        order2 = Order.builder()
                .id(2L)
                .client(client)
                .dishes(new HashSet<>(Arrays.asList(dish1)))
                .total(45.0)
                .status("FINALIZADO")
                .build();
    }

    @Test
    void shouldReturnAllOrders() throws Exception {
        Mockito.when(orderService.getAllOrders()).thenReturn(Arrays.asList(order1, order2));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].status", is("PENDENTE")))
                .andExpect(jsonPath("$[1].status", is("FINALIZADO")));
    }

    @Test
    void shouldReturnOrderById() throws Exception {
        Mockito.when(orderService.getOrderById(1L)).thenReturn(order1);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("PENDENTE")));
    }

    @Test
    void shouldReturnNotFoundWhenOrderDoesNotExist() throws Exception {
        Mockito.when(orderService.getOrderById(99L)).thenThrow(new RuntimeException("Pedido não encontrado"));

        mockMvc.perform(get("/api/orders/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateOrder() throws Exception {
        Mockito.when(orderService.createOrder(any(Order.class))).thenReturn(order1);

        String orderJson = """
            {
                "client": { "id": 1 },
                "dishes": [ { "id": 1 }, { "id": 2 } ],
                "status": "PENDENTE"
            }
            """;

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("PENDENTE")));
    }

    @Test
    void shouldUpdateOrder() throws Exception {
        Mockito.when(orderService.updateOrder(eq(1L), any(Order.class))).thenReturn(order1);

        String orderJson = """
            {
                "client": { "id": 1 },
                "dishes": [ { "id": 1 }, { "id": 2 } ],
                "status": "PENDENTE"
            }
            """;

        mockMvc.perform(put("/api/orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("PENDENTE")));
    }

    @Test
    void shouldDeleteOrder() throws Exception {
        Mockito.when(orderService.deleteOrder(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isNoContent());
    }
}