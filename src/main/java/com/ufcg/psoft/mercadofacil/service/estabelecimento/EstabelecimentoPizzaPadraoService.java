package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.pizza.PizzaDTO;
import com.ufcg.psoft.mercadofacil.dto.pizza.PizzaGetRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.pizza.PizzaRemoveRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.pizza.PizzaPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.mercadofacil.exception.PizzaNaoExisteException;
import com.ufcg.psoft.mercadofacil.exception.QuantidadeDeSaboresInvalidaException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Pizza;
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
    public PizzaDTO salvar(Long id, PizzaPostPutRequestDTO pizzaPostPutRequestDTO) {
        if (!isValid(pizzaPostPutRequestDTO)) {
           throw new QuantidadeDeSaboresInvalidaException();
        }
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoExisteException::new);
        Pizza pizza = modelMapper.map(pizzaPostPutRequestDTO, Pizza.class);
        pizzaRepository.save(pizza);
        estabelecimento.getCardapio().add(pizza);
        PizzaDTO pizzaDto = modelMapper.map(pizza, PizzaDTO.class);
        return pizzaDto;

    }

    @Override
    public PizzaDTO alterar(Long id, Long idPizza, PizzaPostPutRequestDTO pizzaPostPutRequestDTO) {
        if (!isValid(pizzaPostPutRequestDTO)) {
            throw new QuantidadeDeSaboresInvalidaException();
        }
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoExisteException::new);
        Pizza pizza = estabelecimento.getCardapio().stream()
                .filter(elem -> elem.getId().equals(idPizza)).findFirst().orElseThrow(PizzaNaoExisteException::new);
        pizza.setNomePizza(pizzaPostPutRequestDTO.getNomePizza());
        pizza.setSabor(pizzaPostPutRequestDTO.getSabor());
        pizzaRepository.save(pizza);
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
        List<Pizza> cardapio = new ArrayList<>(estabelecimento.getCardapio());
        cardapio.sort(Comparator.comparing(Pizza::getDisponibilidade));
        return cardapio.stream().map(pizza -> modelMapper.map(pizza, PizzaDTO.class)).collect(Collectors.toList());
    }

    private boolean isValid(PizzaPostPutRequestDTO pizzaPostPutRequestDTO) {
        int quantSabores = pizzaPostPutRequestDTO.getSabor().size();
        if(pizzaPostPutRequestDTO.getTamanho().equals("MEDIO") && quantSabores > 1){
            return false;
        }
        if(pizzaPostPutRequestDTO.getTamanho().equals("GRANDE") && quantSabores > 2){
            return false;
        }
        return true;
    }
}
