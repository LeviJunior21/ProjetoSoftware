package com.ufcg.psoft.mercadofacil.estados;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Pedido;

public interface PedidoState {
    void next(Pedido pedido, PedidoState state);
    void previous(Pedido pedido);
    void notifica(Long idCliente, String message);
}
