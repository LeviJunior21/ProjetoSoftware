package com.ufcg.psoft.mercadofacil.service.produto;

import com.ufcg.psoft.mercadofacil.dto.produto.ProdutoTipoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Produto;

@FunctionalInterface
public interface ProdutoAlterarTipoService {
    Produto alterarParcialmente(Long id, ProdutoTipoPatchRequestDTO produtoTipoPatchRequestDTO);
}