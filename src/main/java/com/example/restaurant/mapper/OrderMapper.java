package com.example.restaurant.mapper;

import com.example.restaurant.dto.OrderDTO;
import com.example.restaurant.model.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderDTO toDTO(Order order) {
        if (Objects.isNull(order)) {
            return null;
        }

        return new OrderDTO(
                order.getId(),
                order.getClient() != null ? order.getClient().getId() : null,
                order.getDishes() != null
                        ? order.getDishes().stream()
                            .map(dish -> dish.getId())
                            .collect(Collectors.toList())
                        : null,
                order.getTotal(),
                order.getStatus()
        );
    }
<<<<<<< HEAD

    /**
     * Esse método cria uma entidade Order apenas com dados simples.
     * A associação com Client e Dishes deve ser feita no Service, via repositórios.
     */
=======
>>>>>>> a6fcf881dd26237b5959d0182a92ca328a33e49c
    public Order toEntity(OrderDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }

        Order order = new Order();
        order.setId(dto.getId());
        order.setTotal(dto.getTotal());
        order.setStatus(dto.getStatus());
<<<<<<< HEAD

        // A associação com client e dishes deve ser feita no serviço.
=======
>>>>>>> a6fcf881dd26237b5959d0182a92ca328a33e49c
        return order;
    }
}
