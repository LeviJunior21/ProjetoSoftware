package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.FuncionarioNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.model.Funcionario;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioAlterarPadraoService implements FuncionarioAlterarService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    FuncionarioRepository funcionarioRepository;
    @Override
    public Funcionario alterar(Long id, FuncionarioPostPutRequestDTO funcionarioPostPutRequestDTO) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(FuncionarioNaoExisteException::new);
        modelMapper.map(funcionarioPostPutRequestDTO, funcionario);
        return funcionarioRepository.save(funcionario);
    }
}
