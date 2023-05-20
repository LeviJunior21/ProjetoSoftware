package com.ufcg.psoft.mercadofacil.service.estabelecimento;


import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoAceitarRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoAceitarService {
    EstabelecimentoDTO aceitar(EstabelecimentoAceitarRequestDTO estabelecimentoAceitarRequestDTO, Long funcionarioId);
}
