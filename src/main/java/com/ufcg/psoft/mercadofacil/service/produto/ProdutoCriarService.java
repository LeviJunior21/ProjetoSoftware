package com.ufcg.psoft.mercadofacil.service.produto;

import com.ufcg.psoft.mercadofacil.dto.produto.ProdutoPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Produto;

@FunctionalInterface
public interface ProdutoCriarService {
    Produto salvar(ProdutoPostPutRequestDTO produtoPostPutRequestDTO);
}
