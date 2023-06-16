package com.ufcg.psoft.mercadofacil.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.notifica.notificaInteresse.NotificadorSource;
import com.ufcg.psoft.mercadofacil.notifica.notificaEntrega.PedidoEntregueEvent;
import com.ufcg.psoft.mercadofacil.notifica.notificaEntrega.PedidoEntregueListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "estabelecimentos")
public class Estabelecimento implements PedidoEntregueListener {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("codigoAcesso")
    private Integer codigoAcesso;

    @JsonProperty("nome")
    @Column(nullable = false)
    private String nome;

    @JsonProperty("entregadores")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Entregador> entregadores;

    @JsonProperty("espera")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Funcionario> espera;

    @JsonProperty("pizzas")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Pizza> cardapio;

    @JsonProperty("pedidosCliente")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Pedido> pedidos;

    @JsonProperty("interessados")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private NotificadorSource notificadorSource;

    @Override
    public void notificaPedidoEntregue(PedidoEntregueEvent event) {
        System.out.println(this.getNome() + ", o pedido de número: " + event.getPedido().getId()
                + ", de valor: " + event.getPedido().getValorPedido() + ", foi entregue com sucesso.");
    }
}
