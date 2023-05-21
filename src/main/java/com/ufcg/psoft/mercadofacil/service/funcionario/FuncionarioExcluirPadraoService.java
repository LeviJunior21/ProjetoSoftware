package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioRemoveRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoDiferenteException;
import com.ufcg.psoft.mercadofacil.exception.FuncionarioNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.model.Funcionario;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioExcluirPadraoService implements FuncionarioExcluirService {

    @Autowired
    FuncionarioRepository funcionarioRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public void excluir(Long id, FuncionarioRemoveRequestDTO funcionarioRemoveRequestDTO) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(FuncionarioNaoExisteException::new);
        if (!funcionario.getCodigoAcesso().equals(funcionarioRemoveRequestDTO.getCodigoAcesso())) {
            throw new CodigoAcessoDiferenteException();
        }
        funcionarioRepository.delete(funcionario);
    }
}
