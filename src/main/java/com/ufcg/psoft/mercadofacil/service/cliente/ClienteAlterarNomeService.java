package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Cliente;

@FunctionalInterface
public interface ClienteAlterarNomeService {
    Cliente alterarParcialmente(ClienteNomePatchRequestDTO clienteNomePatchRequestDTO);
}
