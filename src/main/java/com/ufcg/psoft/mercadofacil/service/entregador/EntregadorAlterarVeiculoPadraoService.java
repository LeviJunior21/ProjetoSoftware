package com.ufcg.psoft.mercadofacil.service.entregador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorVeiculoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.EntregadorNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.repository.EntregadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorAlterarVeiculoPadraoService implements EntregadorAlterarVeiculoService{

    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    ModelMapper modelMapper;
    @Override
    public Entregador alterarParcialmente(Long id, EntregadorVeiculoPatchRequestDTO entregadorVeiculoPatchRequestDTO) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(EntregadorNaoExisteException::new);
        modelMapper.map(entregadorVeiculoPatchRequestDTO, entregador);
        return entregadorRepository.save(entregador);
    }
}
