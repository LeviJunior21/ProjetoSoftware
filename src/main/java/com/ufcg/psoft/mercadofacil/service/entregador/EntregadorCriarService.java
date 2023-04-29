package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;

@FunctionalInterface
public interface EntregadorCriarService {
    Entregador salvar(EntregadorPostPutRequestDTO entregadorPostPutRequestDTO);

}
