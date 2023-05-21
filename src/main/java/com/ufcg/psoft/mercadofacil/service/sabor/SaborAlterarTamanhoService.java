package com.ufcg.psoft.mercadofacil.service.sabor;

import com.ufcg.psoft.mercadofacil.dto.sabor.SaborTamanhoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Sabor;

@FunctionalInterface
public interface SaborAlterarTamanhoService {
    Sabor alterarParcialmente(Long id, SaborTamanhoPatchRequestDTO saborTamanhoPatchRequestDTO);
}