package com.ufcg.psoft.mercadofacil.estados;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Pedido;

public class PedidoEmPreparo implements PedidoState{

    @Override
    public void next(Pedido pedido) {
        pedido.setStatePedido(new PedidoPronto());
    }

    @Override
    public void previous(Pedido pedido) {
        //this.notifica(pedido.getIdCliente(), "Seu pedido não pode ser cancelado pois já está em fase de preparo pelo estabelecimento.");
    }

    @Override
    public void notifica(Cliente cliente, Estabelecimento estabelecimento) {
        // Busca cliente no banco
        // Chama a funcao notifica no cliente passando a mensagem
    }
}
