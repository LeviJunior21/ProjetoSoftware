package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.ufcg.psoft.mercadofacil.exception.FuncionarioNaoExisteException;
import org.springframework.stereotype.Service;

@Service
public class EntregadorAlterarNomePadraoService implements EntregadorAlterarNomeService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    FuncionarioRepository entregadorRepository;
    @Override
    public Entregador alterarParcialmente(Long id, EntregadorNomePatchRequestDTO entregadorNomePatchRequestDTO) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(FuncionarioNaoExisteException::new);
        modelMapper.map(entregadorNomePatchRequestDTO, entregador);
        return entregadorRepository.save(entregador);
    }
}
