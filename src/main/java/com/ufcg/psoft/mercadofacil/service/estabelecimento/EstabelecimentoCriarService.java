package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostPutRequestDTO;

@FunctionalInterface
public interface EstabelecimentoCriarService {
    EstabelecimentoDTO salvar(EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO);
}
