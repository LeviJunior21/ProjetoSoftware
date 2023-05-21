package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteEnderecoPatchRequestDTO;

@FunctionalInterface
public interface ClienteAlterarEnderecoService {
    ClienteDTO alterarParcialmente(Long id, ClienteEnderecoPatchRequestDTO clienteEnderecoPatchRequestDTO);
}
