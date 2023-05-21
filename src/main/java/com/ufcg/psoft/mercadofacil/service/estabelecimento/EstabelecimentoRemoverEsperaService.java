package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoRemoveRequestDTO;

@FunctionalInterface
public interface EstabelecimentoRemoverEsperaService {
    EstabelecimentoDTO excluirEspera(Long idEstabelecimento, Long idFuncionario, EstabelecimentoRemoveRequestDTO estabelecimentoRemoveRequestDTO);
}
