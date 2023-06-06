package com.ufcg.psoft.mercadofacil.notifica.notificaEntrega;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Pedido;
import jakarta.persistence.*;

@Entity
@Table(name = "notificadorEntregue")
public class PedidoEntregueSource {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @JsonProperty("estabelecimento_listener")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Estabelecimento estabelecimentoPedidoListener;

    public void notificaEntregue(Pedido pedido) {
        PedidoEntregueEvent event = new PedidoEntregueEvent(this, pedido);
        this.fireNotificacao(event);
    }

    public synchronized void addEstabelecimentoParaPedidoEntregue(Estabelecimento estabelecimento) {
        this.estabelecimentoPedidoListener = estabelecimento;
    }

    public synchronized void removeEstabelecimentoPedidoEntregue(Estabelecimento estabelecimento) {
        this.estabelecimentoPedidoListener = null;
    }

    private void fireNotificacao(PedidoEntregueEvent event) {
        estabelecimentoPedidoListener.notificaPedidoEntregue(event);
    }
}
