package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorPlacaPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioPlacaPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.FuncionarioNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.model.Funcionario;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioAlterarPadraoPlacaService implements FuncionarioAlterarPlacaService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    FuncionarioRepository funcionarioRepository;
    @Override
    public Funcionario alterarParcialmente(Long id, FuncionarioPlacaPatchRequestDTO funcionarioPlacaPatchRequestDTO) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(FuncionarioNaoExisteException::new);
        modelMapper.map(funcionarioPlacaPatchRequestDTO, funcionario);
        return funcionarioRepository.save(funcionario);
    }
}
