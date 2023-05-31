package com.ufcg.psoft.mercadofacil.service.sabor;

import com.ufcg.psoft.mercadofacil.dto.sabor.SaborTipoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Sabor;

@FunctionalInterface
public interface SaborAlterarTipoService {
    Sabor alterarParcialmente(Long id, SaborTipoPatchRequestDTO saborTipoPatchRequestDTO);
}