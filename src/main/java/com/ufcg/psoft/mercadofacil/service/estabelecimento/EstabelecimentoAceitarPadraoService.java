package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoAceitarRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.exception.*;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Funcionario;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoAceitarPadraoService implements EstabelecimentoAceitarService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    FuncionarioRepository funcionarioRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public EstabelecimentoDTO aceitar(EstabelecimentoAceitarRequestDTO estabelecimentoAceitarRequestDTO, Long funcionarioId) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoAceitarRequestDTO.getId()).orElseThrow(EstabelecimentoNaoExisteException::new);
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId).orElseThrow(FuncionarioNaoExisteException::new);
        if (estabelecimentoAceitarRequestDTO.getCodigoAcesso().equals(estabelecimento.getCodigoAcesso())) {
            if (estabelecimento.getEspera().contains(funcionario)) {
                Entregador entregador = new Entregador();
                modelMapper.map(funcionario, entregador);
                estabelecimento.getEntregadores().add(entregador);
                estabelecimento.getEspera().remove(funcionario);
                estabelecimentoRepository.save(estabelecimento);
            }
            else {
                throw new EstabelecimentoNaoFuncionarioEsperaException();
            }
        }
        else {
            throw new CodigoAcessoDiferenteException();
        }

        EstabelecimentoDTO estabelecimentoDTO = modelMapper.map(estabelecimento, EstabelecimentoDTO.class);
        return estabelecimentoDTO;
    }
}
