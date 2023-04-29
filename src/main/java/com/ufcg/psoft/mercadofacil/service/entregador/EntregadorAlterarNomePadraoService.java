package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.repository.EntregadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.ufcg.psoft.mercadofacil.exception.EntregadorNaoExisteException;
import org.springframework.stereotype.Service;

@Service
public class EntregadorAlterarNomePadraoService implements EntregadorAlterarNomeService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    EntregadorRepository entregadorRepository;
    @Override
    public Entregador alterarParcialmente(Long id, EntregadorNomePatchRequestDTO entregadorNomePatchRequestDTO) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(EntregadorNaoExisteException::new);
        modelMapper.map(entregadorNomePatchRequestDTO, entregador);
        return entregadorRepository.save(entregador);
    }
}
