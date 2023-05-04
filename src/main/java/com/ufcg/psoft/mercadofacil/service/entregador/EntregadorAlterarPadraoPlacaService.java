package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorPlacaPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.FuncionarioNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorAlterarPadraoPlacaService implements EntregadorAlterarPlacaService{
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    FuncionarioRepository entregadorRepository;
    @Override
    public Entregador alterarParcialmente(Long id, EntregadorPlacaPatchRequestDTO entregadorPlacaPatchRequestDTO) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(FuncionarioNaoExisteException::new);
        modelMapper.map(entregadorPlacaPatchRequestDTO, entregador);
        return entregadorRepository.save(entregador);
    }
}
