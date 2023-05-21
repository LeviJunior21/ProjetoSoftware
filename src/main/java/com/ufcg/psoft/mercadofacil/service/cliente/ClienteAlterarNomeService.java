package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteNomePatchRequestDTO;

@FunctionalInterface
public interface ClienteAlterarNomeService {
    ClienteDTO alterarParcialmente(Long id, ClienteNomePatchRequestDTO clienteNomePatchRequestDTO);
}
