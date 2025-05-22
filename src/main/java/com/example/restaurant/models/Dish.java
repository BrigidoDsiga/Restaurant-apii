package com.restaurante.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "itens_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relação muitos-para-um com Produto, carregamento EAGER para garantir dados disponíveis
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal precoUnitario;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal subtotal;

    // Método auxiliar para calcular subtotal baseado na quantidade e preço unitário
    public void calcularSubtotal() {
        if (quantidade != null && precoUnitario != null) {
            this.subtotal = precoUnitario.multiply(new BigDecimal(quantidade));
        } else {
            this.subtotal = BigDecimal.ZERO;
        }
    }

}
