package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioDTO;
import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioPostPutRequestDTO;

@FunctionalInterface
public interface FuncionarioCriarService {
    FuncionarioDTO salvar(FuncionarioPostPutRequestDTO funcionarioPostPutRequestDTO);
}
