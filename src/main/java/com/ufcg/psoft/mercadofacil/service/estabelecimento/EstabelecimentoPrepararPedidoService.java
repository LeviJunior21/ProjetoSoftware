package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostGetRequestDTO;

@FunctionalInterface
public interface EstabelecimentoPrepararPedidoService {
    EstabelecimentoDTO preparar(Long idEstabelecimento, EstabelecimentoPostGetRequestDTO estabelecimentoPostGetRequestDTO, Long idCliente);
}
