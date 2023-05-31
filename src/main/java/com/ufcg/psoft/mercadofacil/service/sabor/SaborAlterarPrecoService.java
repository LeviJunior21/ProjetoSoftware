package com.ufcg.psoft.mercadofacil.service.sabor;

import com.ufcg.psoft.mercadofacil.dto.sabor.SaborPrecoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Sabor;

@FunctionalInterface
public interface SaborAlterarPrecoService {
    Sabor alterarParcialmente(Long id, SaborPrecoPatchRequestDTO saborPrecoPatchRequestDTO);
}