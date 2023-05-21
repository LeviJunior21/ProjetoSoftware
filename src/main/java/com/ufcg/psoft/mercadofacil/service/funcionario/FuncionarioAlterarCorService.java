package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioCorPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioDTO;

@FunctionalInterface
public interface FuncionarioAlterarCorService {
    FuncionarioDTO alterarParcialmente(Long id, FuncionarioCorPatchRequestDTO funcionarioCorPatchRequestDTO);
}
