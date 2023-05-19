package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoGetRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoGetService {
    Estabelecimento get(EstabelecimentoGetRequestDTO estabelecimentoGetRequestDTO);
}
