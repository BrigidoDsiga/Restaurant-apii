package com.restaurante.api.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Assumindo que Cliente já existe como entidade
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Cascade ALL para persistir e remover itens automaticamente com o pedido
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private List<ItemPedido> itens;

    @Column(nullable = false)
    private LocalDateTime dataPedido;

    @Column(length = 50)
    private String status;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valorTotal;

    /**
     * Calcula o valor total do pedido somando os subtotais dos itens.
     */
    public void calcularValorTotal() {
        if (itens != null && !itens.isEmpty()) {
            this.valorTotal = itens.stream()
                    .map(ItemPedido::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            this.valorTotal = BigDecimal.ZERO;
        }
    }

    /**
     * Callback JPA para garantir dados antes de salvar ou atualizar.
     */
    @PrePersist
    @PreUpdate
    private void preSave() {
        calcularValorTotal();
        if (dataPedido == null) {
            dataPedido = LocalDateTime.now();
        }
        if (status == null || status.isBlank()) {
            status = "PENDENTE";  // status padrão para pedidos novos
        }
    }
}
