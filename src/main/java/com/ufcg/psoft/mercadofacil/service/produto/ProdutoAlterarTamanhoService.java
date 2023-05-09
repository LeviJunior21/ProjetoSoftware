package com.ufcg.psoft.mercadofacil.service.produto;

import com.ufcg.psoft.mercadofacil.dto.produto.ProdutoTamanhoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Produto;
@FunctionalInterface
public interface ProdutoAlterarTamanhoService {
    Produto alterarParcialmente(Long id, ProdutoTamanhoPatchRequestDTO produtoTamanhoPatchRequestDTO);
}