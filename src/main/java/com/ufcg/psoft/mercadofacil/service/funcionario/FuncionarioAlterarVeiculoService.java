package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioDTO;
import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioVeiculoPatchRequestDTO;

@FunctionalInterface
public interface FuncionarioAlterarVeiculoService {
    FuncionarioDTO alterarParcialmente(Long id, FuncionarioVeiculoPatchRequestDTO funcionarioVeiculoPatchRequestDTO);
}
