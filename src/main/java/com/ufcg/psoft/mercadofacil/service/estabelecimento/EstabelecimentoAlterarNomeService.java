package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoAlterarNomeService {
    public Estabelecimento alterarParcialmente(Long id, EstabelecimentoNomePatchRequestDTO produtoNomePatchRequestDTO);
}