package com.example.restaurant.mapper;

import com.example.restaurant.dto.OrderDTO;
import com.example.restaurant.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }
        List<Long> dishIds = order.getDishes() != null
                ? order.getDishes().stream().map(dish -> dish.getId()).collect(Collectors.toList())
                : null;
        return new OrderDTO(
                order.getId(),
                order.getClient() != null ? order.getClient().getId() : null,
                dishIds,
                order.getTotal(),
                order.getStatus()
        );
    }

    public Order toEntity(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }
        Order order = new Order();
        order.setId(orderDTO.getId());
        // Atenção: Aqui você deve buscar o Client e os Dishes pelo ID usando os serviços/repositórios apropriados.
        // Este método só faz o mapeamento básico dos IDs.
        order.setTotal(orderDTO.getTotal());
        order.setStatus(orderDTO.getStatus());
        return order;
    }
}