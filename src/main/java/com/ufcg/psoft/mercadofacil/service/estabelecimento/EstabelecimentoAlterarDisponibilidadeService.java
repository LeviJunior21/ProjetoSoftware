package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoMensagemGetDTO;

@FunctionalInterface
public interface EstabelecimentoAlterarDisponibilidadeService {
    EstabelecimentoMensagemGetDTO alterarDisponibilidade(Long idPizza, Long idEstabelecimento);
}
