package com.ufcg.psoft.mercadofacil.notifica.notificaEntrega;

@FunctionalInterface
public interface PedidoEntregueListener {
    void notificaPedidoEntregue(PedidoEntregueEvent event);
}
