package com.example.restaurant.repository;

import com.example.restaurant.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Busca todos os pedidos relacionados a um cliente específico.
     *
     * @param clientId ID do cliente
     * @return lista de pedidos do cliente
     */
    List<Order> findByClientId(Long clientId);

    /**
     * Busca pedidos pelo status.
     * Exemplo: "PENDING", "COMPLETED", etc.
     *
     * @param status status do pedido
     * @return lista de pedidos com o status informado
     */
    List<Order> findByStatus(String status);

    /**
     * Busca pedidos feitos em um intervalo de datas.
     *
     * @param startData data inicial
     * @param endData data final
     * @return lista de pedidos entre as datas informadas
     */
    List<Order> findByDataPedidoBetween(LocalDateTime startData, LocalDateTime endData);

    /**
     * Busca pedidos por cliente e status.
     *
     * @param clientId ID do cliente
     * @param status status do pedido
     * @return lista de pedidos do cliente com o status informado
     */
    List<Order> findByClientIdAndStatus(Long clientId, String status);

}
