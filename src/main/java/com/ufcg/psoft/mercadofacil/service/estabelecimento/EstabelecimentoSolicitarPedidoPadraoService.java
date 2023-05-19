package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.FuncionarioSolicitaEntradaRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoDiferenteException;
import com.ufcg.psoft.mercadofacil.exception.FuncionarioNaoExisteException;
import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Funcionario;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoSolicitarPedidoPadraoService implements EstabelecimentoSolicitarPedidoService{
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Override
    public Estabelecimento solicitarPedido(Long idEstabelecimento, FuncionarioSolicitaEntradaRequestDTO funcionarioSolicitaEntradaRequestDTO) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioSolicitaEntradaRequestDTO.getId()).orElseThrow(FuncionarioNaoExisteException::new);
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(EstabelecimentoNaoExisteException::new);
        if (!funcionario.getCodigoAcesso().equals(funcionarioSolicitaEntradaRequestDTO.getCodigoAcesso())) {
            throw new CodigoAcessoDiferenteException();
        }
        estabelecimento.getEspera().add(funcionario);
        estabelecimentoRepository.save(estabelecimento);
        return estabelecimento;
    }
}
