package com.ufcg.psoft.mercadofacil.estados;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Pedido;
public interface PedidoState {
    void next(Pedido pedido);
    void previous(Pedido pedido);
    void notifica(Cliente cliente, Estabelecimento estabelecimento);
}