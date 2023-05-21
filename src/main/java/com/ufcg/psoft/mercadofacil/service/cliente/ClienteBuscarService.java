package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteGetRequestDTO;

@FunctionalInterface
public interface ClienteBuscarService {
    ClienteDTO get(Long id, ClienteGetRequestDTO clienteGetRequestDTO);
}
