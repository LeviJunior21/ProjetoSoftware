package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoGetRequestDTO;

@FunctionalInterface
public interface EstabelecimentoGetService {
    EstabelecimentoDTO get(Long id, EstabelecimentoGetRequestDTO estabelecimentoGetRequestDTO);
}
