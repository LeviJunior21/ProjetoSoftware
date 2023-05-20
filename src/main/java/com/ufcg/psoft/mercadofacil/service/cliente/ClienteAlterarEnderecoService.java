package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteEnderecoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Cliente;

@FunctionalInterface
public interface ClienteAlterarEnderecoService {
    Cliente alterarParcialmente(ClienteEnderecoPatchRequestDTO clienteEnderecoPatchRequestDTO);
}
