package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.exception.ProdutoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.repository.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EntregadorListarPadraoService implements EntregadorListarService {

    @Autowired
    EntregadorRepository entregadorRepository;

    @Override
    public List<Entregador> listar(Long id) {
        if(id!=null && id > 0) {
            entregadorRepository.findById(id).orElseThrow(ProdutoNaoExisteException::new);
        }
        return entregadorRepository.findAll();
    }
}
