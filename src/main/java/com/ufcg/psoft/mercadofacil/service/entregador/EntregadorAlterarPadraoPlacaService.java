package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorPlacaPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.EntregadorNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.repository.EntregadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorAlterarPadraoPlacaService implements EntregadorAlterarPlacaService{
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    EntregadorRepository entregadorRepository;
    @Override
    public Entregador alterarParcialmente(Long id, EntregadorPlacaPatchRequestDTO entregadorPlacaPatchRequestDTO) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(EntregadorNaoExisteException::new);
        modelMapper.map(entregadorPlacaPatchRequestDTO, entregador);
        return entregadorRepository.save(entregador);
    }
}
