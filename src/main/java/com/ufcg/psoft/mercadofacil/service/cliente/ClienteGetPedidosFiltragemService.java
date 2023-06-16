package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.pedido.PedidoDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoGetFiltragemDTO;

import java.util.List;

@FunctionalInterface
public interface ClienteGetPedidosFiltragemService {
    List<PedidoDTO> getPedidosFiltragem(Long idCliente, ClientePedidoGetFiltragemDTO pedidoGetFiltragemDTO);
}
