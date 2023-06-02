package com.ufcg.psoft.mercadofacil.estados;

import com.ufcg.psoft.mercadofacil.model.Pedido;

public class PedidoPronto implements PedidoState{
    private PedidoState pedidoStateNext;

    @Override
    public void next(Pedido pedido, PedidoState state) {
        this.pedidoStateNext = new PedidoEmPreparo();
        //pedido.setStatePedido(this.pedidoStateNext);
    }

    @Override
    public void previous(Pedido pedido) {
        //this.notifica(pedido.getIdCliente(), "O pedido não pode mais ser cancelado pois, ele já está pronto pelo estabelecimento.");
    }

    @Override
    public void notifica(Long idCliente, String message) {
        // Busca cliente no banco
        // Chama a funcao notifica no cliente passando a mensagem
    }
}
