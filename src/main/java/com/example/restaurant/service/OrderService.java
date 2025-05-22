package com.example.restaurant.service;

import com.example.restaurant.exception.ResourceNotFoundException;
import com.example.restaurant.model.Client;
import com.example.restaurant.model.Dish;
import com.example.restaurant.model.Order;
import com.example.restaurant.repository.ClientRepository;
import com.example.restaurant.repository.DishRepository;
import com.example.restaurant.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final DishRepository dishRepository;

    public OrderService(OrderRepository orderRepository, ClientRepository clientRepository, DishRepository dishRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.dishRepository = dishRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + id));
    }

    @Transactional
    public Order createOrder(Order order) {
        validateAndSetClient(order);
        validateAndSetDishes(order);
        calculateTotal(order);
        setDefaultStatus(order);
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrder(Long id, Order orderDetails) {
        Order order = getOrderById(id);

        if (orderDetails.getClient() != null && orderDetails.getClient().getId() != null) {
            Client client = clientRepository.findById(orderDetails.getClient().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + orderDetails.getClient().getId()));
            order.setClient(client);
        }

        if (orderDetails.getDishes() != null && !orderDetails.getDishes().isEmpty()) {
            Set<Dish> dishes = new HashSet<>();
            for (Dish dish : orderDetails.getDishes()) {
                Dish foundDish = dishRepository.findById(dish.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Prato não encontrado com id: " + dish.getId()));
                dishes.add(foundDish);
            }
            order.setDishes(dishes);
            calculateTotal(order);
        }

        if (orderDetails.getStatus() != null && !orderDetails.getStatus().isEmpty()) {
            order.setStatus(orderDetails.getStatus());
        }

        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }

    public List<Order> getOrdersByClientId(Long clientId) {
        return orderRepository.findByClientId(clientId);
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    private void validateAndSetClient(Order order) {
        if (order.getClient() == null || order.getClient().getId() == null) {
            throw new IllegalArgumentException("Cliente é obrigatório.");
        }
        Client client = clientRepository.findById(order.getClient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + order.getClient().getId()));
        order.setClient(client);
    }

    private void validateAndSetDishes(Order order) {
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
    }

    private void calculateTotal(Order order) {
        BigDecimal total = order.getDishes().stream()
                .map(Dish::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(total);
    }

    private void setDefaultStatus(Order order) {
        if (order.getStatus() == null || order.getStatus().isBlank()) {
            order.setStatus("PENDENTE");
        }
    }
}
