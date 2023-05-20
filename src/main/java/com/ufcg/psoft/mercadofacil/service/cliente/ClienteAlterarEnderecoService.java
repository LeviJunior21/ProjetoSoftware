package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteEnderecoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Cliente;

@FunctionalInterface
public interface ClienteAlterarEnderecoService {
    ClienteDTO alterarParcialmente(ClienteEnderecoPatchRequestDTO clienteEnderecoPatchRequestDTO);
}
