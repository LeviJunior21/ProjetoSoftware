package com.ufcg.psoft.mercadofacil.service.listarPizza;

import com.ufcg.psoft.mercadofacil.model.Pizza;
import com.ufcg.psoft.mercadofacil.model.Produto;

import java.util.Set;

@FunctionalInterface
public interface EstabelecimentoListarTodasPizzasDocesService {
    Set<Pizza> buscarTodasPizzasDoces(Long id);
}
