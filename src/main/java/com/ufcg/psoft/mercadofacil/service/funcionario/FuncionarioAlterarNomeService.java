package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Funcionario;

@FunctionalInterface
public interface FuncionarioAlterarNomeService {
    Funcionario alterarParcialmente(Long id, FuncionarioNomePatchRequestDTO funcionarioNomePatchRequestDTO);
}