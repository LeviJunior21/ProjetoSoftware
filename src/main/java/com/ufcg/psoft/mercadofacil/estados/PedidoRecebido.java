package com.ufcg.psoft.mercadofacil.estados;

import com.ufcg.psoft.mercadofacil.model.Pedido;

public class PedidoRecebido implements PedidoState{
    private PedidoState getPedidoStatePrev;

    public PedidoRecebido(PedidoState prev) {
        this.getPedidoStatePrev = prev;
    }

    @Override
    public void next(Pedido pedido) {
        pedido.setStatePedido(new PedidoEmPreparo());
    }

    @Override
    public void previous(Pedido pedido) {
        pedido.setStatePedido(getPedidoStatePrev);
    }

    @Override
    public void notifica(Long idCliente, String message) {
        // Busca cliente no banco
        // Chama a funcao notifica no cliente passando a mensagem
    }
}
