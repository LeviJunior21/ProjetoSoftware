package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.model.Funcionario;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioCriarPadraoService implements FuncionarioCriarService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    FuncionarioRepository funcionarioRepository;
    public Funcionario salvar(FuncionarioPostPutRequestDTO funcionarioPostPutRequestDTO) {
        Funcionario funcionario = modelMapper.map(funcionarioPostPutRequestDTO, Funcionario.class);
        return funcionarioRepository.save(funcionario);
    }
}
