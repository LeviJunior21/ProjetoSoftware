package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioDTO;
import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioNomePatchRequestDTO;

@FunctionalInterface
public interface FuncionarioAlterarNomeService {
    FuncionarioDTO alterarParcialmente(Long id, FuncionarioNomePatchRequestDTO funcionarioNomePatchRequestDTO);
}
