package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.FuncionarioSolicitaEntradaRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoSolicitarPedidoService {
    public EstabelecimentoDTO solicitarPedido(Long idEstabelecimento, FuncionarioSolicitaEntradaRequestDTO funcionarioSolicitaEntradaRequestDTO);
}
