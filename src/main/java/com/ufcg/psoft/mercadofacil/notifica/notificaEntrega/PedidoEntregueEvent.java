package com.ufcg.psoft.mercadofacil.notifica.notificaEntrega;

import com.ufcg.psoft.mercadofacil.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PedidoEntregueEvent {
    private PedidoEntregueSource pedidoEntregueSource;
    private Pedido pedido;
}
