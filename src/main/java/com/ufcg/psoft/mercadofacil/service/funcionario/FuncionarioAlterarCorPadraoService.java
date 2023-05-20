package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioCorPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioDTO;
import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoDiferenteException;
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
    public FuncionarioDTO alterarParcialmente(Long id, FuncionarioCorPatchRequestDTO funcionarioCorPatchRequestDTO) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(FuncionarioNaoExisteException::new);
        if (!funcionario.getCodigoAcesso().equals(funcionarioCorPatchRequestDTO.getCodigoAcesso()))  {
            throw new CodigoAcessoDiferenteException();
        }
        modelMapper.map(funcionarioCorPatchRequestDTO, funcionario);
        Funcionario funcionario1 = funcionarioRepository.save(funcionario);
        FuncionarioDTO funcionarioDTO = modelMapper.map(funcionario1, FuncionarioDTO.class);
        return funcionarioDTO;
    }
}
