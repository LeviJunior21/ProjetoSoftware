package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioDTO;
import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioVeiculoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoDiferenteException;
import com.ufcg.psoft.mercadofacil.exception.FuncionarioNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Funcionario;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioAlterarVeiculoPadraoService implements FuncionarioAlterarVeiculoService {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    ModelMapper modelMapper;
    @Override
    public FuncionarioDTO alterarParcialmente(Long id, FuncionarioVeiculoPatchRequestDTO funcionarioVeiculoPatchRequestDTO) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(FuncionarioNaoExisteException::new);
        if (!funcionario.getCodigoAcesso().equals(funcionarioVeiculoPatchRequestDTO.getCodigoAcesso()))  {
            throw new CodigoAcessoDiferenteException();
        }
        modelMapper.map(funcionarioVeiculoPatchRequestDTO, funcionario);
        Funcionario funcionario1 = funcionarioRepository.save(funcionario);
        FuncionarioDTO funcionarioDTO = modelMapper.map(funcionario1, FuncionarioDTO.class);
        return funcionarioDTO;
    }
}
