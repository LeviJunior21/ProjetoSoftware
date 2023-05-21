package com.ufcg.psoft.mercadofacil.service.listarPizza;

import com.ufcg.psoft.mercadofacil.model.Pizza;
import java.util.Set;

@FunctionalInterface
public interface EstabelecimentoListarTodasPizzasSalgadasService {
    Set<Pizza> buscarTodasPizzasSalgadas(Long id);
}
