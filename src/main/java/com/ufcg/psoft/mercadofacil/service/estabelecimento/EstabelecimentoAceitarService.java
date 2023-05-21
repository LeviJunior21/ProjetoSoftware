package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoAceitarPostRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;

@FunctionalInterface
public interface EstabelecimentoAceitarService {
    EstabelecimentoDTO aceitar(Long idEstabelecimento, Long idFuncionario, EstabelecimentoAceitarPostRequestDTO estabelecimentoAceitarPostRequestDTO);
}
