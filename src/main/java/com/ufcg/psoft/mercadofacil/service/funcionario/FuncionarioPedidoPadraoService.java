package com.ufcg.psoft.mercadofacil.service.funcionario;

import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Funcionario;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class FuncionarioPedidoPadraoService implements FuncionarioPedidoService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public Estabelecimento pedido(Funcionario funcionario, Long idEstabelecimento) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(EstabelecimentoNaoExisteException::new);
        estabelecimento.getEspera().add(funcionario);
        estabelecimentoRepository.save(estabelecimento);
        return estabelecimento;
    }
}
