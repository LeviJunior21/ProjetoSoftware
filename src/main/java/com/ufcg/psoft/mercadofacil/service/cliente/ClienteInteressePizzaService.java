package com.ufcg.psoft.mercadofacil.service.cliente;

public interface ClienteInteressePizzaService {
    void interessar(Long idCliente, Long idEstabelecimento, Long idPizza);
    void desinteressar(Long idCliente, Long idEstabelecimento, Long idPizza);
}
