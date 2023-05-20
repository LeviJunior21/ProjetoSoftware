package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteGetRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Cliente;

@FunctionalInterface
public interface ClienteBuscarService {
    ClienteDTO get(ClienteGetRequestDTO clienteGetRequestDTO);
}
