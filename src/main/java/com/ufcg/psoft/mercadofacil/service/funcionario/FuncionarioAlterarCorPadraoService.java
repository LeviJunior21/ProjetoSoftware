package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioCorPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.FuncionarioNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Funcionario;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioAlterarCorPadraoService implements FuncionarioAlterarCorService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    FuncionarioRepository funcionarioRepository;
    @Override
    public Funcionario alterarParcialmente(Long id, FuncionarioCorPatchRequestDTO funcionarioCorPatchRequestDTO) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(FuncionarioNaoExisteException::new);
        modelMapper.map(funcionarioCorPatchRequestDTO, funcionario);
        return funcionarioRepository.save(funcionario);
    }
}
