package com.ufcg.psoft.mercadofacil.service.sabor;

import com.ufcg.psoft.mercadofacil.exception.SaborNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Sabor;
import com.ufcg.psoft.mercadofacil.repository.SaborRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborExcluirPadraoService implements SaborExcluirService {
    @Autowired
    SaborRepository saborRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public void excluir(Long id) {
        Sabor sabor = saborRepository.findById(id).orElseThrow(SaborNaoExisteException::new);
        saborRepository.delete(sabor);
    }
}
