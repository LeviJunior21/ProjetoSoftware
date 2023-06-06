package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostGetRequestDTO;

@FunctionalInterface
public interface EstabelecimentoAlterarParaEmPreparoService {
    void alterarParaEmPreparo(Long idEstabelecimento, Long idPedido, EstabelecimentoPostGetRequestDTO estabelecimentoPostGetRequestDTO);
}
