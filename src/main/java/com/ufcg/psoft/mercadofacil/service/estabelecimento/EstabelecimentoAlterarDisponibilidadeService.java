package com.ufcg.psoft.mercadofacil.service.estabelecimento;


import com.ufcg.psoft.mercadofacil.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoAlterarDisponibilidadeService {
    public Estabelecimento alterarDisponibilidade(Long idPizza, Long idEstabelecimento);
}
