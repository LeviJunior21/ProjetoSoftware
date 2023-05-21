package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoRemoveRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoRemoverEsperaService {
    EstabelecimentoDTO excluirEspera(Long idEstabelecimento, Long idFuncionario, EstabelecimentoRemoveRequestDTO estabelecimentoRemoveRequestDTO);
}
