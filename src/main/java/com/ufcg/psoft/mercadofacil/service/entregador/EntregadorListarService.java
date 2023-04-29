package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.model.Entregador;
import java.util.List;

@FunctionalInterface
public interface EntregadorListarService {
    List<Entregador> listar(Long id);
}
