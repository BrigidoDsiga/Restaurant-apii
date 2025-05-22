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
    @JoinColumn(name = "cliente_id")  // chave estrangeira
    private Cliente cliente;

    // Cascade ALL para persistir e remover itens automaticamente com o pedido
    // mappedBy deve estar na classe ItemPedido (se bidirecional), se for unidirecional fica assim.
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id") // FK na tabela itens_pedido
    private List<ItemPedido> itens;

    @Column(nullable = false)
    private LocalDateTime dataPedido;

    @Column(length = 50)
    private String status;

    @Column(nullable = false)
    private BigDecimal valorTotal;
}

