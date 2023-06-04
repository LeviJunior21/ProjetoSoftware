package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoRequestDTO;

public interface ClienteCancelarPedidoService {
    ClienteDTO cancelaPedido(Long idCliente, Long idEstabelecimento, ClientePedidoRequestDTO clientePedidoRequestDTO);
}
