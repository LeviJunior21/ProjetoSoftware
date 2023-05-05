package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.exception.ProdutoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import org.springframework.stereotype.Service;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class EstabelecimentoListarPadraoService implements EstabelecimentoListarService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Override
    public List<Estabelecimento> listar(Long id) {
        return estabelecimentoRepository.findAll();
    }
}
