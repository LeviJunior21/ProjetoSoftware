package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.model.Cliente;

@FunctionalInterface
public interface ClienteBuscarService {
    Cliente get(Long id);
}
