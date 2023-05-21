package com.ufcg.psoft.mercadofacil.service.produto;

import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.dto.produto.ProdutoNomePatchRequestDTO;

@FunctionalInterface
public interface ProdutoAlterarNomeService {
    Produto alterarParcialmente(Long id, ProdutoNomePatchRequestDTO produtoNomePatchRequestDTO);
}