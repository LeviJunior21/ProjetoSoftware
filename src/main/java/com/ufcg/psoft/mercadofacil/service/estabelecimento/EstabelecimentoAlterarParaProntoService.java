package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostGetRequestDTO;

@FunctionalInterface
public interface EstabelecimentoAlterarParaProntoService {
    void alterarParaPronto(Long idEstabelecimento, Long idPedido, EstabelecimentoPostGetRequestDTO estabelecimentoPostGetRequestDTO);
}
