package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostGetRequestDTO;

@FunctionalInterface
public interface EstabelecimentoAlterarParaEmRotaService {

    void alterarParaEmRota(Long idEstabelecimento, Long idCliente, Long idPedido,  EstabelecimentoPostGetRequestDTO estabelecimentoPostGetRequestDTO);
}
