package com.ufcg.psoft.mercadofacil.notifica.notificaRota;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import jakarta.persistence.*;

@Entity
@Table(name = "notificadorRota")
public class PedidoSource {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @JsonProperty("cliente_listener")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Cliente pedidoListener;

    public void notificaEmRota(Entregador entregador) {
        PedidoEvent event = new PedidoEvent(this, entregador);
        this.fireNotificacao(event);
    }

    public synchronized void addPedidoDoClienteEmRota(Cliente cliente) {
        this.pedidoListener = cliente;
    }

    public synchronized void removeInteresse(Cliente cliente) {
        this.pedidoListener = null;
    }

    private void fireNotificacao(PedidoEvent event) {
        pedidoListener.notificaPedidoEmRota(event);
    }

    @Override
    public String toString() {
        return pedidoListener.toString();
    }
}
