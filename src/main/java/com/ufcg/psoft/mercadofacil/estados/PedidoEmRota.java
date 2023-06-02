package com.ufcg.psoft.mercadofacil.estados;

import com.ufcg.psoft.mercadofacil.model.Pedido;

public class PedidoEmRota implements PedidoState{
    @Override
    public void next(Pedido pedido, PedidoState state) {
        //pedido.setStatePedido(new PedidoEntregue());
    }

    @Override
    public void previous(Pedido pedido) {
        //this.notifica(pedido.getIdCliente(), "O seu pedido não pode ser cancelado pois, o entregador está indo a sua residência.");
    }

    @Override
    public void notifica(Long idCliente, String message) {
        // Busca cliente no banco
        // Chama a funcao notifica no cliente passando a mensagem
    }
}
