package com.ufcg.psoft.mercadofacil.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.estados.CriandoPedido;
import com.ufcg.psoft.mercadofacil.estados.PedidoEntregue;
import com.ufcg.psoft.mercadofacil.estados.PedidoRecebido;
import com.ufcg.psoft.mercadofacil.estados.PedidoState;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedidos")
public class Pedido extends PedidoConcrect{
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
}
