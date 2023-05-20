package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteRemoveRequestDTO;

@FunctionalInterface
public interface ClienteExcluirService {
    void excluir(ClienteRemoveRequestDTO clienteRemoveRequestDTO);
}
