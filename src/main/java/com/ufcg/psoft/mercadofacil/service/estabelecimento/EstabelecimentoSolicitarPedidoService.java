package com.ufcg.psoft.mercadofacil.service.estabelecimento;

<<<<<<< HEAD
=======
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.FuncionarioSolicitaEntradaRequestDTO;
>>>>>>> user11
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoSolicitarPedidoService {
    public EstabelecimentoDTO solicitarPedido(Long idEstabelecimento, FuncionarioSolicitaEntradaRequestDTO funcionarioSolicitaEntradaRequestDTO);
}
