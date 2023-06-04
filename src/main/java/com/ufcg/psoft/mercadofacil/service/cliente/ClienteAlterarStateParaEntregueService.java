package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;

@FunctionalInterface
public interface ClienteAlterarStateParaEntregueService {

    EstabelecimentoDTO alterarPedidoState(Long idCliente, Long idEstabelecimento, ClientePedidoPatchRequestDTO clientePedidoPatchRequestDTO);
}
