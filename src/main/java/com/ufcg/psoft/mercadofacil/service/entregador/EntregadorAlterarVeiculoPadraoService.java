package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorVeiculoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.FuncionarioNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorAlterarVeiculoPadraoService implements EntregadorAlterarVeiculoService{

    @Autowired
    FuncionarioRepository entregadorRepository;

    @Autowired
    ModelMapper modelMapper;
    @Override
    public Entregador alterarParcialmente(Long id, EntregadorVeiculoPatchRequestDTO entregadorVeiculoPatchRequestDTO) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(FuncionarioNaoExisteException::new);
        modelMapper.map(entregadorVeiculoPatchRequestDTO, entregador);
        return entregadorRepository.save(entregador);
    }
}
