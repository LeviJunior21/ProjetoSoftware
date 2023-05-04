package com.ufcg.psoft.mercadofacil.service.estabelecimento;


@FunctionalInterface
public interface EstabelecimentoAceitarService {
    public void aceitar(Long estabelecimentoId, Long funcionarioId);
}
