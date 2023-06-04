package com.ufcg.psoft.mercadofacil.notifica.notificaRota;
@FunctionalInterface
public interface PedidoListener {
    void notificaPedidoEmRota(PedidoEvent event);
}
