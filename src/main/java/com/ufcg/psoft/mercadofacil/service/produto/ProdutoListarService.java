package com.ufcg.psoft.mercadofacil.service.produto;

import com.ufcg.psoft.mercadofacil.model.Produto;

import java.util.List;

@FunctionalInterface
public interface ProdutoListarService {
    List<Produto> listar(Long id);

}
