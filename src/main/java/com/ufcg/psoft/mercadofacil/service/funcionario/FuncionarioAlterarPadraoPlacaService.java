package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioDTO;
import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioPlacaPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoDiferenteException;
import com.ufcg.psoft.mercadofacil.exception.FuncionarioNaoExisteException;
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
    public FuncionarioDTO alterarParcialmente(Long id, FuncionarioPlacaPatchRequestDTO funcionarioPlacaPatchRequestDTO) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(FuncionarioNaoExisteException::new);
        if (!funcionario.getCodigoAcesso().equals(funcionarioPlacaPatchRequestDTO.getCodigoAcesso()))  {
            throw new CodigoAcessoDiferenteException();
        }
        modelMapper.map(funcionarioPlacaPatchRequestDTO, funcionario);
        Funcionario funcionario1 = funcionarioRepository.save(funcionario);
        FuncionarioDTO funcionarioDTO = modelMapper.map(funcionario1, FuncionarioDTO.class);
        return funcionarioDTO;
    }
}
