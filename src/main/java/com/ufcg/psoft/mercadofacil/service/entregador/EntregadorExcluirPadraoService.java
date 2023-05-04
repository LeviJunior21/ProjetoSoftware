package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.ufcg.psoft.mercadofacil.exception.FuncionarioNaoExisteException;
import org.springframework.stereotype.Service;

@Service
public class EntregadorExcluirPadraoService implements EntregadorExcluirService {

    @Autowired
    FuncionarioRepository entregadorRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public void excluir(Long id) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(FuncionarioNaoExisteException::new);
        entregadorRepository.delete(entregador);
    }
}
