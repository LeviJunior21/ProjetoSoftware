package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.pedido.PedidoDTO;

@FunctionalInterface
public interface ClienteGetUmPedidoService {
    PedidoDTO getUmPedido(Long idCliente, Long idPedido);
}
