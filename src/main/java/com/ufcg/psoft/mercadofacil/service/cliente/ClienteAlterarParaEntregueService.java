package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoPostDTO;

@FunctionalInterface
public interface ClienteAlterarParaEntregueService {
    void alterarParaEntregue(Long idCliente, Long idEstabelecimento, Long idPedido, ClientePedidoPostDTO clientePedidoPostDTO);
}
