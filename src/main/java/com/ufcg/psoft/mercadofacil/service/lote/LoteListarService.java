package com.ufcg.psoft.mercadofacil.service.lote;

import com.ufcg.psoft.mercadofacil.model.Lote;
import java.util.List;

@FunctionalInterface
public interface LoteListarService {
    List<Lote> listar(Long id);

}
