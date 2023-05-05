package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioVeiculoPatchRequestDTO;

import com.ufcg.psoft.mercadofacil.model.Funcionario;

@FunctionalInterface
public interface FuncionarioAlterarVeiculoService {
    Funcionario alterarParcialmente(Long id, FuncionarioVeiculoPatchRequestDTO funcionarioVeiculoPatchRequestDTO);
}
