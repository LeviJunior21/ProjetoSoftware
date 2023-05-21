package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioRemoveRequestDTO;

@FunctionalInterface
public interface FuncionarioExcluirService {
    void excluir(Long id, FuncionarioRemoveRequestDTO  funcionarioRemoveRequestDTO);
}
