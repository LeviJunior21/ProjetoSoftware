package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorCorPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;

@FunctionalInterface
public interface EntregadorAlterarCorService {
    Entregador alterarParcialmente(Long id, EntregadorCorPatchRequestDTO entregadorCorPatchRequestDTO);
}
