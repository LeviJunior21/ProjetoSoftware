package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;

@FunctionalInterface
public interface EntregadorPedidoService {
    public Estabelecimento pedido(Entregador entregador, Long idEstabelecimento);
}
