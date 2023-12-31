package com.ufcg.psoft.mercadofacil.estados;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Pedido;

public class CriandoPedido implements PedidoState {

    @Override
    public void next(Pedido pedido) {
        pedido.setStatePedido(new PedidoRecebido(this));
    }

    @Override
    public void previous(Pedido pedido) {
        // this.notifica(pedido.getIdCliente(), "Você ainda não enviou o seu pedido a um estabelecimento");
    }

    @Override
    public void notifica(Cliente cliente, Estabelecimento estabelecimento) {
        // Busca cliente no banco
        // Chama a funcao notifica no cliente passando a mensagem
    }
}
