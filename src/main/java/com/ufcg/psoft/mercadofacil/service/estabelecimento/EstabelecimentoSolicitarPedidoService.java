package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.FuncionarioSolicitaEntradaPostRequestDTO;

@FunctionalInterface
public interface EstabelecimentoSolicitarPedidoService {
    EstabelecimentoDTO solicitarPedido(Long idEstabelecimento, FuncionarioSolicitaEntradaPostRequestDTO funcionarioSolicitaEntradaPostRequestDTO);
}
