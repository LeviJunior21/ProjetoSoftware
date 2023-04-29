package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.EntregadorRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoAceitarPadraoService implements EstabelecimentoAceitarService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    EntregadorRepository entregadorRepository;

    @Override
    public void aceitar(Long estabelecimentoId, Long entregadorId) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).get();
        Entregador entregador = entregadorRepository.findById(entregadorId).get();
        estabelecimento.getEntregadores().add(entregador);
        estabelecimentoRepository.save(estabelecimento);
    }
}
