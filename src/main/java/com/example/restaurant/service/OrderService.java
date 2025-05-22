// OrderService
package com.example.restaurant.service;

import com.example.restaurant.exception.ResourceNotFoundException;
import com.example.restaurant.model.Client;
import com.example.restaurant.model.Dish;
import com.example.restaurant.model.Order;
import com.example.restaurant.repository.ClientRepository;
import com.example.restaurant.repository.DishRepository;
import com.example.restaurant.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DishRepository dishRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + id));
    }

    public Order createOrder(Order order) {
        // Validação de cliente
        if (order.getClient() == null || order.getClient().getId() == null) {
            throw new IllegalArgumentException("Cliente é obrigatório.");
        }
        Client client = clientRepository.findById(order.getClient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + order.getClient().getId()));
        order.setClient(client);

        // Validação de pratos
        if (order.getDishes() == null || order.getDishes().isEmpty()) {
            throw new IllegalArgumentException("O pedido deve conter pelo menos um prato.");
        }
        Set<Dish> dishes = new HashSet<>();
        for (Dish dish : order.getDishes()) {
            Dish foundDish = dishRepository.findById(dish.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Prato não encontrado com id: " + dish.getId()));
            dishes.add(foundDish);
        }
        order.setDishes(dishes);

        // Cálculo do total
        double total = dishes.stream().mapToDouble(Dish::getPrice).sum();
        order.setTotal(total);

        // Status inicial
        if (order.getStatus() == null || order.getStatus().isEmpty()) {
            order.setStatus("PENDENTE");
        }

        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Order order = getOrderById(id);

        // Atualiza cliente se informado
        if (orderDetails.getClient() != null && orderDetails.getClient().getId() != null) {
            Client client = clientRepository.findById(orderDetails.getClient().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + orderDetails.getClient().getId()));
            order.setClient(client);
        }

        // Atualiza pratos se informado
        if (orderDetails.getDishes() != null && !orderDetails.getDishes().isEmpty()) {
            Set<Dish> dishes = new HashSet<>();
            for (Dish dish : orderDetails.getDishes()) {
                Dish foundDish = dishRepository.findById(dish.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Prato não encontrado com id: " + dish.getId()));
                dishes.add(foundDish);
            }
            order.setDishes(dishes);
            // Atualiza total
            double total = dishes.stream().mapToDouble(Dish::getPrice).sum();
            order.setTotal(total);
        }

        // Atualiza status se informado
        if (orderDetails.getStatus() != null && !orderDetails.getStatus().isEmpty()) {
            order.setStatus(orderDetails.getStatus());
        }

        return orderRepository.save(order);
    }

    public boolean deleteOrder(Long id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
        return true;
    }

    public List<Order> getOrdersByClientId(Long clientId) {
        return orderRepository.findByClientId(clientId);
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
}