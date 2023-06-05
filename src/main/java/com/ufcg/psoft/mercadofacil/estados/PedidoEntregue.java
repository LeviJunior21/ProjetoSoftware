package com.ufcg.psoft.mercadofacil.estados;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Pedido;

public class PedidoEntregue implements PedidoState{

    @Override
    public void next(Pedido pedido) {
        // Não tem implementação para esse método nesse estado.
    }

    @Override
    public void previous(Pedido pedido) {
    }

    @Override
    public void notifica(Cliente cliente, Estabelecimento estabelecimento) {
        // Busca cliente no banco
        // Chama a funcao notifica no cliente passando a mensagem
    }
}
