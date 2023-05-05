package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioCorPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Funcionario;

@FunctionalInterface
public interface FuncionarioAlterarCorService {
    Funcionario alterarParcialmente(Long id, FuncionarioCorPatchRequestDTO funcionarioCorPatchRequestDTO);
}
