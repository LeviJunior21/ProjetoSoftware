package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.EntregadorNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.repository.EntregadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorAlterarPadraoService implements EntregadorAlterarService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    EntregadorRepository entregadorRepository;
    @Override
    public Entregador alterar(Long id, EntregadorPostPutRequestDTO entregadorPostPutRequestDTO) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(EntregadorNaoExisteException::new);
        modelMapper.map(entregadorPostPutRequestDTO, entregador);
        return entregadorRepository.save(entregador);
    }
}
