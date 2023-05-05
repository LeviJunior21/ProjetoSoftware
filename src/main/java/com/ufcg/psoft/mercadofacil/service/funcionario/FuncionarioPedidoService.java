package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Funcionario;

@FunctionalInterface
public interface FuncionarioPedidoService {
    public Estabelecimento pedido(Funcionario funcionario, Long idEstabelecimento);
}
