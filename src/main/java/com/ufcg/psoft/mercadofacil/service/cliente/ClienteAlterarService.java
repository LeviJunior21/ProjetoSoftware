package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePostPutRequestDTO;

@FunctionalInterface
public interface ClienteAlterarService {
    ClienteDTO alterar(Long id, ClientePostPutRequestDTO clientePostPutRequestDTO);
}
