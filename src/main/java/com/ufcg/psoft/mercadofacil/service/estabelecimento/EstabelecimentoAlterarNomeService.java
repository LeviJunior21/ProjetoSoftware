package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoNomePatchRequestDTO;

@FunctionalInterface
public interface EstabelecimentoAlterarNomeService {
    EstabelecimentoDTO alterarParcialmente(Long id, EstabelecimentoNomePatchRequestDTO produtoNomePatchRequestDTO);
}
