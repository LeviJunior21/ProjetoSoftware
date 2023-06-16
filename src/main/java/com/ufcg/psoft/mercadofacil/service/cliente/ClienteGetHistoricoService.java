package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.pedido.PedidoDTO;

import java.util.List;

@FunctionalInterface
public interface ClienteGetHistoricoService {
    List<PedidoDTO> getHistorico(Long idCliente, Long idEstabelecimento);
}
