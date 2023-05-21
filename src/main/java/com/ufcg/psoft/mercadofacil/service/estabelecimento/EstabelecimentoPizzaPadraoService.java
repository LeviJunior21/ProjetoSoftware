package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.pizza.PizzaDTO;
import com.ufcg.psoft.mercadofacil.dto.pizza.PizzaGetRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.pizza.PizzaRemoveRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.pizza.SaborPizzaPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.mercadofacil.exception.PizzaNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Pizza;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import com.ufcg.psoft.mercadofacil.repository.PizzaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EstabelecimentoPizzaPadraoService implements EstabelecimentoPizzaService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    PizzaRepository pizzaRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public PizzaDTO salvar(Long id, SaborPizzaPostPutRequestDTO saborPizzaPostPutRequestDTO) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoExisteException::new);
        Produto produto = modelMapper.map(saborPizzaPostPutRequestDTO, Produto.class);
        Pizza pizza = pizzaRepository.save(Pizza.builder().sabor(Set.of(produto)).disponibilidade("disponivel").build());
        estabelecimento.getCardapio().add(pizza);
        //estabelecimentoRepository.save(estabelecimento);
        PizzaDTO pizzaDTO = modelMapper.map(pizza, PizzaDTO.class);
        return pizzaDTO;
    }

    @Override
    public PizzaDTO alterar(Long id, Long idPizza, SaborPizzaPostPutRequestDTO saborPizzaPostPutRequestDTO) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoExisteException::new);
        Pizza pizza = estabelecimento.getCardapio().stream()
                .filter(elem -> elem.getId().equals(idPizza)).findFirst().orElseThrow(PizzaNaoExisteException::new);
        Produto produto = modelMapper.map(saborPizzaPostPutRequestDTO, Produto.class);
        pizza.setSabor(Set.of(produto));
        //pizzaRepository.save(pizza);
        //estabelecimento.getCardapio().add(pizza);
        //estabelecimentoRepository.save(estabelecimento);
        PizzaDTO pizzaDTO = modelMapper.map(pizza, PizzaDTO.class);
        return pizzaDTO;
    }

    @Override
    public void excluir(Long id, PizzaRemoveRequestDTO pizzaRemoveRequestDTO) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoExisteException::new);
        Pizza pizza = estabelecimento.getCardapio().stream()
                .filter(elem -> elem.getId().equals(pizzaRemoveRequestDTO.getId())).findFirst().orElseThrow(PizzaNaoExisteException::new);
        estabelecimento.getCardapio().remove(pizza);
    }

    @Override
    public PizzaDTO get(Long id, PizzaGetRequestDTO pizzaGetRequestDTO) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoExisteException::new);
        Pizza pizza = estabelecimento.getCardapio().stream()
                .filter(elem -> elem.getId().equals(pizzaGetRequestDTO.getId())).findFirst().orElseThrow(PizzaNaoExisteException::new);
        PizzaDTO pizzaDTO = modelMapper.map(pizza, PizzaDTO.class);
        return pizzaDTO;
    }

    @Override
    public List<PizzaDTO> listar(Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoExisteException::new);
        //List<Pizza> cardapio = (List<Pizza>) estabelecimento.getCardapio();
        List<Pizza> cardapio = new ArrayList<>(estabelecimento.getCardapio());
        cardapio.sort(Comparator.comparing(Pizza::getDisponibilidade));
        return cardapio.stream().map(pizza -> modelMapper.map(pizza, PizzaDTO.class)).collect(Collectors.toList());
    }
}
