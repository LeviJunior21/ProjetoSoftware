package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.model.Entregador;

@FunctionalInterface
public interface EstabelecimentoAceitarService {
    public void aceitar(Long estabelecimentoId, Long entregadorId);
}
