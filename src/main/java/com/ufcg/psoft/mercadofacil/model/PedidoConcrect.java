package com.ufcg.psoft.mercadofacil.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufcg.psoft.mercadofacil.estados.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class PedidoConcrect {

    @JsonIgnore
    private PedidoState pedidoStateNext = new CriandoPedido();

    public void next() {
        this.pedidoStateNext.next((Pedido) this);
    }

    public void notifica(Cliente cliente, Estabelecimento estabelecimento) {
        this.pedidoStateNext.notifica(cliente, estabelecimento);
    }
    public void cancelarPedido() {
        this.pedidoStateNext.previous((Pedido) this);
    }

    public void setStatePedido(PedidoState pedidoState) {
        this.pedidoStateNext = pedidoState;
    }

    public PedidoState getPedidoStateNext() {
        return pedidoStateNext;
    }
}
