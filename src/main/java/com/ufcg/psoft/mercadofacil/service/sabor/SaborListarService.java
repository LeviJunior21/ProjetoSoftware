package com.ufcg.psoft.mercadofacil.service.sabor;

import com.ufcg.psoft.mercadofacil.model.Sabor;

import java.util.List;

@FunctionalInterface
public interface SaborListarService {
    List<Sabor> listar(Long id);

}
