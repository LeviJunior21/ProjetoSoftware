package com.ufcg.psoft.mercadofacil.service.estabelecimento;

@FunctionalInterface
public interface EstabelecimentoAlterarDisponibilidadeService {
    void alterarDisponibilidade(Long idPizza, Long idEstabelecimento);
}
