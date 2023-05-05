package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Funcionario;

@FunctionalInterface
public interface FuncionarioAlterarService {
    Funcionario alterar(Long id, FuncionarioPostPutRequestDTO funcionarioPostPutRequestDTO);
}
