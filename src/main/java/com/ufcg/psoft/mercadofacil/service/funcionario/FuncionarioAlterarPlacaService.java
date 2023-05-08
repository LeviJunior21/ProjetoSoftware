package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioPlacaPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Funcionario;

@FunctionalInterface
public interface FuncionarioAlterarPlacaService {
    Funcionario alterarParcialmente(Long id, FuncionarioPlacaPatchRequestDTO funcionarioPlacaPatchRequestDTO);
}