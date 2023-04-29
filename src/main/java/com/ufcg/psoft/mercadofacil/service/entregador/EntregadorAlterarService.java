package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;

@FunctionalInterface
public interface EntregadorAlterarService {
    Entregador alterar(Long id, EntregadorPostPutRequestDTO entregadorPostPutRequestDTO);
}
