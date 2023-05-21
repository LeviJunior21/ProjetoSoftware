package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteDTO;
import java.util.List;

@FunctionalInterface
public interface ClienteListarService {
    List<ClienteDTO> listar();
}
