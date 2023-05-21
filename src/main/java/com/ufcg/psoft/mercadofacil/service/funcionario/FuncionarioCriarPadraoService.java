package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioDTO;
import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioPostPutRequestDTO;
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
    public FuncionarioDTO salvar(FuncionarioPostPutRequestDTO funcionarioPostPutRequestDTO) {
        Funcionario funcionario = modelMapper.map(funcionarioPostPutRequestDTO, Funcionario.class);
        Funcionario funcionario1 = funcionarioRepository.save(funcionario);
        FuncionarioDTO funcionarioDTO = modelMapper.map(funcionario1, FuncionarioDTO.class);
        return funcionarioDTO;
    }
}
