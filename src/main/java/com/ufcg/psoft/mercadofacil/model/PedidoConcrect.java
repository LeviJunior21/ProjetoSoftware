package com.ufcg.psoft.mercadofacil.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.estados.*;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class PedidoConcrect {
    @JsonProperty("idCliente")
    private Long idCliente;

    @JsonProperty("idEstabelecimento")
    private Long idEstabelecimento;

    @JsonIgnore
    private PedidoState pedidoStateNext = new CriandoPedido();

    public void next() {
        this.pedidoStateNext.next((Pedido) this);
    }

    public void notifica(Cliente cliente, Estabelecimento estabelecimento) {
        this.pedidoStateNext.notifica(cliente, estabelecimento);
    }

    public Long getIdEstabelecimento() {
        return this.idEstabelecimento;
    }

    public void cancelarPedido() {
        this.pedidoStateNext.previous((Pedido) this);
    }

    public void setStatePedido(PedidoState pedidoState) {
        this.pedidoStateNext = pedidoState;
        /*if (this.pedidoStateNext.getClass().equals(PedidoRecebido.class)) {
            System.out.println("Pedido recebido");
        }
        if (this.pedidoStateNext.getClass().equals(PedidoEmPreparo.class)) {
            System.out.println("Pedido em preparo");
        };
        if (this.pedidoStateNext.getClass().equals(PedidoPronto.class)) {
            System.out.println("Pedido Pronto");
        }
        if (this.pedidoStateNext.getClass().equals(PedidoEmRota.class)) {
            System.out.println("Pedido em Rota");
        };
        if (this.pedidoStateNext.getClass().equals(PedidoEntregue.class)) {
            System.out.println("Pedido Entregue");
        }
        *
         */
    }

    public PedidoState getPedidoStateNext() {
        return pedidoStateNext;
    }
}
