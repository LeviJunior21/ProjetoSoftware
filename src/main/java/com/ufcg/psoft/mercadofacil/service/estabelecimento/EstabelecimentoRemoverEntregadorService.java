package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoRemoverEntregadorService {
    Estabelecimento excluirEspera(Long idEstabelecimento, Long idEntregador);
}
