package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoCriarService {
    Estabelecimento salvar(EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO);
}