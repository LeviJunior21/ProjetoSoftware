package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostGetRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoPrepararPedidoService {
    void preparar(Long idEstabelecimento, EstabelecimentoPostGetRequestDTO estabelecimentoPostGetRequestDTO, Long idCliente);
}
