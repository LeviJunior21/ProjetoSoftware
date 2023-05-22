package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoRequestDTO;

@FunctionalInterface
public interface ClienteSolicitarPedidoService {
    void solicitar(Long idEstabelecimento, Long idCliente, ClientePedidoRequestDTO clientePedidoRequestDTO);
}
