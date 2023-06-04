package com.ufcg.psoft.mercadofacil.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.estados.CriandoPedido;
import com.ufcg.psoft.mercadofacil.estados.PedidoRecebido;
import com.ufcg.psoft.mercadofacil.estados.PedidoState;
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
        this.pedidoStateNext.next((Pedido) this, this.pedidoStateNext);
    }

    public Long getIdEstabelecimento() {
        return this.idEstabelecimento;
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
