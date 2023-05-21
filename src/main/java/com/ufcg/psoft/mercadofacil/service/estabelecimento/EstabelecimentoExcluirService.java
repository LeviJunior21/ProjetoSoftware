package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoRemoveRequestDTO;

@FunctionalInterface
public interface EstabelecimentoExcluirService {
    void excluir(Long id, EstabelecimentoRemoveRequestDTO estabelecimentoRemoveRequestDTO);
}
