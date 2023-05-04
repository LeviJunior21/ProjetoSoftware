package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.exception.EntregadorNaoExisteException;
import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Funcionario;
import com.ufcg.psoft.mercadofacil.repository.EntregadorRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class EntregadorPedidoPadraoService implements EntregadorPedidoService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    EntregadorRepository entregadorRepository;

    @Override
    public Estabelecimento pedido(Funcionario funcionario, Long idEstabelecimento) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(EstabelecimentoNaoExisteException::new);
        estabelecimento.getEspera().add(funcionario);
        estabelecimentoRepository.save(estabelecimento);
        return estabelecimento;
    }
}
