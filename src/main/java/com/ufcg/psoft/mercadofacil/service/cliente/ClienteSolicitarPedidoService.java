package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;

@FunctionalInterface
public interface ClienteSolicitarPedidoService {
    EstabelecimentoDTO solicitar(Long idEstabelecimento, Long idCliente, ClientePedidoRequestDTO clientePedidoRequestDTO);
}
