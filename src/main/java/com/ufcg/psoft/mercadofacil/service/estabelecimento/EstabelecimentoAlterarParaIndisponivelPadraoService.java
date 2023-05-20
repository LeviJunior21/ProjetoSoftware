package com.ufcg.psoft.mercadofacil.service.estabelecimento;


import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.mercadofacil.exception.PizzaNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Pizza;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import com.ufcg.psoft.mercadofacil.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoAlterarParaIndisponivelPadraoService implements EstabelecimentoAlterarDisponibilidadeService{
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    PizzaRepository pizzaRepository;
    @Override
    public Estabelecimento alterarDisponibilidade(Long idPizza, Long idEstabelecimento) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(EstabelecimentoNaoExisteException::new);
        Pizza pizza = pizzaRepository.findById(idPizza).orElseThrow(PizzaNaoExisteException::new);
        pizza.setDisponibilidade("indisponivel");
        pizzaRepository.save(pizza);
        estabelecimento.getCardapio().add(pizza);
        estabelecimentoRepository.save(estabelecimento);
        return estabelecimento;
    }
}
