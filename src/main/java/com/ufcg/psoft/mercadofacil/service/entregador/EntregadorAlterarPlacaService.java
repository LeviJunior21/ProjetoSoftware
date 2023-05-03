package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorPlacaPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;

@FunctionalInterface
public interface EntregadorAlterarPlacaService {
    Entregador alterarParcialmente(Long id, EntregadorPlacaPatchRequestDTO entregadorPlacaPatchRequestDTO);
}
