package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;

@FunctionalInterface
public interface EntregadorAlterarNomeService{
    Entregador alterarParcialmente(Long id, EntregadorNomePatchRequestDTO entregadorNomePatchRequestDTO);
}
