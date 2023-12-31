package com.ufcg.psoft.mercadofacil.estados;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Pedido;

public class PedidoPronto implements PedidoState{

    @Override
    public void next(Pedido pedido) {

        pedido.setStatePedido(new PedidoEmRota());
    }

    @Override
    public void previous(Pedido pedido) {
        //this.notifica(pedido.getIdCliente(), "O pedido não pode mais ser cancelado pois, ele já está pronto pelo estabelecimento.");
    }

    @Override
    public void notifica(Cliente cliente, Estabelecimento estabelecimento) {
        // Busca cliente no banco
        // Chama a funcao notifica no cliente passando a mensagem
    }
}
