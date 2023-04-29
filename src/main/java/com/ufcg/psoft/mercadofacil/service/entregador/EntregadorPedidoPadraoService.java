package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.EntregadorRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class EntregadorPedidoPadraoService implements EntregadorPedidoService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    EntregadorRepository entregadorRepository;

    @Override
    public Estabelecimento pedido(Long idEntregador, Long idEstabelecimento) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).get();
        Entregador entregador = entregadorRepository.findById(idEntregador).get();
        estabelecimento.getEspera().add(entregador);
        estabelecimentoRepository.save(estabelecimento);
        return estabelecimento;
    }
}
