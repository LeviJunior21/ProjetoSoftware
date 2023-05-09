package com.ufcg.psoft.mercadofacil.service.produto;

import com.ufcg.psoft.mercadofacil.dto.produto.ProdutoPrecoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Produto;
@FunctionalInterface
public interface ProdutoAlterarPrecoService {
    Produto alterarParcialmente(Long id, ProdutoPrecoPatchRequestDTO produtoPrecoPatchRequestDTO);
}