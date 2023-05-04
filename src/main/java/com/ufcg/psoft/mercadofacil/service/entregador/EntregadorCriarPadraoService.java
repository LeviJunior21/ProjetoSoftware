package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorCriarPadraoService implements EntregadorCriarService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    FuncionarioRepository entregadorRepository;
    public Entregador salvar(EntregadorPostPutRequestDTO entregadorPostPutRequestDTO) {
        Entregador estabelecimento = modelMapper.map(entregadorPostPutRequestDTO, Entregador.class);
        return entregadorRepository.save(estabelecimento);
    }
}
