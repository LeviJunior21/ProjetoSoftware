package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Funcionario;

@FunctionalInterface
public interface EntregadorPedidoService {
    public Estabelecimento pedido(Funcionario funcionario, Long idEstabelecimento);
}
