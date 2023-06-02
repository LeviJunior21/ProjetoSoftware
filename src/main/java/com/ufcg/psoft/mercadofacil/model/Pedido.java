package com.ufcg.psoft.mercadofacil.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.estados.CriandoPedido;
import com.ufcg.psoft.mercadofacil.estados.PedidoRecebido;
import com.ufcg.psoft.mercadofacil.estados.PedidoState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedidos")
public class Pedido {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("enderecoEntrega")
    private String enderecoEntrega;

    @JsonProperty("valorPedido")
    private Double valorPedido;

    @JsonProperty("pizzas")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Pizza> pizzas = new HashSet<>();

    @JsonProperty("metodoPagamento")
    private String metodoPagamento;

    /**
    @JsonProperty("idCliente")
    private Long idCliente;

    @JsonProperty("idEstabelecimento")
    private Long idEstabelecimento;

    @Builder.Default
    PedidoState pedidoStateNext = new CriandoPedido();

    public void next() {
        if (pedidoStateNext.getClass().equals(PedidoRecebido.class)) {
            this.pedidoStateNext.notifica(this.getIdEstabelecimento(), "O cliente recebeu o pedido:\n Info pedido ...");
        }
        this.pedidoStateNext.next(this, pedidoStateNext);
    }

    public void cancelarPedido() {
        this.pedidoStateNext.previous(this);
    }

    public Long getIdCliente() {
        return this.idCliente;
    }

    public void setStatePedido(PedidoState pedidoState) {
        this.pedidoStateNext = pedidoState;
    }
    **/

}
