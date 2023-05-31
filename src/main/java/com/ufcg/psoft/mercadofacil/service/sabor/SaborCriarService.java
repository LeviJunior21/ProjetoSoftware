package com.ufcg.psoft.mercadofacil.service.sabor;

import com.ufcg.psoft.mercadofacil.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Sabor;

@FunctionalInterface
public interface SaborCriarService {
    Sabor salvar(SaborPostPutRequestDTO saborPostPutRequestDTO);
}
