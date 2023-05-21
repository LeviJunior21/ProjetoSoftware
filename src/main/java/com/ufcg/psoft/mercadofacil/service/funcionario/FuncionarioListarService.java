package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.model.Funcionario;
import java.util.List;

@FunctionalInterface
public interface FuncionarioListarService {
    List<Funcionario> listar(Long id);
}
