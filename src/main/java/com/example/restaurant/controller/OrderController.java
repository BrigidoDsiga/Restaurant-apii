import com.example.restaurant.dto.OrderDTO;
import com.example.restaurant.exception.ResourceNotFoundException;
import com.example.restaurant.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para gerenciar pedidos (orders).
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * Construtor para injeção de dependência do serviço de pedidos.
     *
     * @param orderService serviço responsável pelas operações de pedidos
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Retorna a lista de todos os pedidos.
     *
     * @return lista de pedidos
     */
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Retorna um pedido pelo seu ID.
     *
     * @param id ID do pedido
     * @return pedido encontrado ou 404 se não existir
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        Optional<OrderDTO> orderOpt = orderService.getOrderById(id);
        return orderOpt
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + id));
    }

    /**
     * Cria um novo pedido.
     *
     * @param orderDTO dados do pedido a serem criados
     * @return pedido criado com URI no header Location
     */
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        URI location = URI.create(String.format("/api/orders/%d", createdOrder.getId()));
        return ResponseEntity.created(location).body(createdOrder);
    }

    /**
     * Atualiza um pedido existente.
     *
     * @param id       ID do pedido a atualizar
     * @param orderDTO dados atualizados do pedido
     * @return pedido atualizado ou 404 se não existir
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO) {
        Optional<OrderDTO> updatedOrderOpt = orderService.updateOrder(id, orderDTO);
        return updatedOrderOpt
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + id));
    }

    /**
     * Remove um pedido pelo seu ID.
     *
     * @param id ID do pedido
     * @return resposta 204 se deletado, 404 se não encontrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        boolean deleted = orderService.deleteOrder(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Pedido não encontrado com id: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
