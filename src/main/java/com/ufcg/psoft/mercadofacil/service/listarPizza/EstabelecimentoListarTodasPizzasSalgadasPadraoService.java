package com.ufcg.psoft.mercadofacil.service.listarPizza;

import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Pizza;
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
    public Set<Pizza> buscarTodasPizzasSalgadas(Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoExisteException::new);

        return estabelecimento.getCardapio().stream().filter(pizza -> "salgado".equals(pizza.getSabor().iterator().next().getTipo())).collect(Collectors.toSet());
    }
}
