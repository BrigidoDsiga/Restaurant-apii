package com.example.restaurant.mapper;

import com.example.restaurant.dto.OrderDTO;
import com.example.restaurant.model.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Mapper para conversão entre Order e OrderDTO.
 */
@Component
public class OrderMapper {

    /**
     * Converte uma entidade Order em OrderDTO.
     *
     * @param order entidade Order
     * @return DTO correspondente ou null se order for null
     */
    public OrderDTO toDTO(Order order) {
        if (Objects.isNull(order)) {
            return null;
        }

        return new OrderDTO(
                order.getId(),
                order.getClient() != null ? order.getClient().getId() : null,
                order.getDishes() != null
                        ? order.getDishes().stream()
                        .map(Dish::getId)
                        .collect(Collectors.toList())
                        : null,
                order.getTotal(),
                order.getStatus()
        );
    }

    /**
     * Converte um OrderDTO em entidade Order.
     * Note que as associações com Client e Dishes devem ser feitas no serviço,
     * via repositórios, para manter o mapper simples.
     *
     * @param dto DTO de pedido
     * @return entidade Order correspondente ou null se dto for null
     */
    public Order toEntity(OrderDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }

        Order order = new Order();
        order.setId(dto.getId());
        order.setTotal(dto.getTotal());
        order.setStatus(dto.getStatus());



        return order;
    }
}
