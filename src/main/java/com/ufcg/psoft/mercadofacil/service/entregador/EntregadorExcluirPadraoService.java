package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.repository.EntregadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.ufcg.psoft.mercadofacil.exception.EntregadorNaoExisteException;
import org.springframework.stereotype.Service;

@Service
public class EntregadorExcluirPadraoService implements EntregadorExcluirService {

    @Autowired
    EntregadorRepository entregadorRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public void excluir(Long id) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(EntregadorNaoExisteException::new);
        entregadorRepository.delete(entregador);
    }
}
