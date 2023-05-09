package com.ufcg.psoft.mercadofacil.service.listarPizza;

import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EstabelecimentoListarTodasPizzasSalgadasPadraoService implements EstabelecimentoListarTodasPizzasSalgadasService{

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public Set<Produto> buscarTodasPizzasSalgadas(Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoExisteException::new);

        return estabelecimento.getPizzas().stream().filter(produto -> "salgado".equals(produto.getTipo()))
                .collect(Collectors.toSet());
    }
}
