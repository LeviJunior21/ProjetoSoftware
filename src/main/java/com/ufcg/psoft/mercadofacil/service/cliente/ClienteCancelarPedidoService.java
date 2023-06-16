package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoPostDTO;
@FunctionalInterface
public interface ClienteCancelarPedidoService {
    void cancelaPedido(Long idCliente, Long idPedido, Long idEstabelecimento, ClientePedidoPostDTO clientePedidoPostDTO);
}
