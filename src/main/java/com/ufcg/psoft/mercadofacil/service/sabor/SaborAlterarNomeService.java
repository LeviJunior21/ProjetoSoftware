package com.ufcg.psoft.mercadofacil.service.sabor;

import com.ufcg.psoft.mercadofacil.model.Sabor;
import com.ufcg.psoft.mercadofacil.dto.sabor.SaborNomePatchRequestDTO;

@FunctionalInterface
public interface SaborAlterarNomeService {
    Sabor alterarParcialmente(Long id, SaborNomePatchRequestDTO saborNomePatchRequestDTO);
}