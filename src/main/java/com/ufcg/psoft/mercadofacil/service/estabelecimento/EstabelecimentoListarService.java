package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import java.util.List;

@FunctionalInterface
public interface EstabelecimentoListarService {
    List<EstabelecimentoDTO> listar();
}
