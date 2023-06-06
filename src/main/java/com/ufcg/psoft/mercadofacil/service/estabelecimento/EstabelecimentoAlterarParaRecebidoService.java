package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostGetRequestDTO;

@FunctionalInterface
public interface EstabelecimentoAlterarParaRecebidoService {
    void alterarParaRecebido(Long idEstabelecimento, EstabelecimentoPostGetRequestDTO estabelecimentoPostGetRequestDTO, Long idPedido);
}
