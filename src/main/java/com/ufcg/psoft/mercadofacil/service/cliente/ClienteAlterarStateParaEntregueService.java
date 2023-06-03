package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;

public interface ClienteAlterarStateParaEntregueService {

    public EstabelecimentoDTO alterarPedido(Long idCliente, Long idEstabelecimento, ClientePedidoPatchRequestDTO clientePedidoPatchRequestDTO);
}
