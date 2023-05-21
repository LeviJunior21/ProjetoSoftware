package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.FuncionarioSolicitaEntradaPostRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoDiferenteException;
import com.ufcg.psoft.mercadofacil.exception.FuncionarioNaoExisteException;
import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Funcionario;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoSolicitarPedidoPadraoService implements EstabelecimentoSolicitarPedidoService{
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    FuncionarioRepository funcionarioRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public EstabelecimentoDTO solicitarPedido(Long idEstabelecimento, FuncionarioSolicitaEntradaPostRequestDTO funcionarioSolicitaEntradaPostRequestDTO) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioSolicitaEntradaPostRequestDTO.getId()).orElseThrow(FuncionarioNaoExisteException::new);
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(EstabelecimentoNaoExisteException::new);
        if (!funcionario.getCodigoAcesso().equals(funcionarioSolicitaEntradaPostRequestDTO.getCodigoAcesso())) {
            throw new CodigoAcessoDiferenteException();
        }
        estabelecimento.getEspera().add(funcionario);
        Estabelecimento estabelecimento1 = estabelecimentoRepository.save(estabelecimento);
        EstabelecimentoDTO estabelecimentoDTO = modelMapper.map(estabelecimento1, EstabelecimentoDTO.class);

        return estabelecimentoDTO;
    }
}
