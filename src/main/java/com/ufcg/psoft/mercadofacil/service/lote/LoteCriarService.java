package com.ufcg.psoft.mercadofacil.service.lote;

import com.ufcg.psoft.mercadofacil.dto.lote.LotePostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Lote;

@FunctionalInterface
public interface LoteCriarService {
   Lote salvar(LotePostPutRequestDTO lotePostPutRequestDTO);

}
