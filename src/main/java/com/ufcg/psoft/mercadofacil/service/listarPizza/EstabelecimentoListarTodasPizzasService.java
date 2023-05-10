package com.ufcg.psoft.mercadofacil.service.listarPizza;

import com.ufcg.psoft.mercadofacil.model.Produto;

import java.util.Set;

@FunctionalInterface
public interface EstabelecimentoListarTodasPizzasService {
    Set<Produto> buscarTodasPizzas(Long id);
}
