package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.exception.FuncionarioNaoExisteException;
import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Funcionario;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoRemoverEntregadorPadraoService implements EstabelecimentoRemoverEntregadorService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    FuncionarioRepository entregadorRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Estabelecimento excluirEspera(Long idEstabelecimento, Long idEntregador) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(EstabelecimentoNaoExisteException::new);
        Funcionario funcionario = entregadorRepository.findById(idEntregador).orElseThrow(FuncionarioNaoExisteException::new);
        estabelecimento.getEspera().remove(funcionario);
        estabelecimentoRepository.save(estabelecimento);
        return estabelecimento;
    }
}
