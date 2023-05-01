package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorVeiculoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;

@FunctionalInterface
public interface EntregadorAlterarVeiculoService {
    Entregador alterarParcialmente(Long id, EntregadorVeiculoPatchRequestDTO entregadorVeiculoPatchRequestDTO);
}
