package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.pizza.PizzaDTO;
import com.ufcg.psoft.mercadofacil.dto.pizza.PizzaGetRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.pizza.PizzaRemoveRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.pizza.SaborPizzaPostPutRequestDTO;

import java.util.List;

public interface EstabelecimentoPizzaService {
    PizzaDTO salvar(Long id, SaborPizzaPostPutRequestDTO saborPizzaPostPutRequestDTO);
    PizzaDTO alterar(Long id, Long idPizza, SaborPizzaPostPutRequestDTO saborPizzaPostPutRequestDTO);
    void excluir(Long id, PizzaRemoveRequestDTO pizzaRemoveRequestDTO);
    PizzaDTO get(Long id, PizzaGetRequestDTO pizzaGetResquestDTO);
    List<PizzaDTO> listar(Long id);
}
