package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import com.ufcg.psoft.mercadofacil.service.listarPizza.EstabelecimentoListarTodasPizzasDocesService;
import com.ufcg.psoft.mercadofacil.service.listarPizza.EstabelecimentoListarTodasPizzasSalgadasService;
import com.ufcg.psoft.mercadofacil.service.listarPizza.EstabelecimentoListarTodasPizzasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(
        value = "/v1/listarPizzas",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class CardapioV1Controller {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    EstabelecimentoListarTodasPizzasDocesService estabelecimentoListarTodasPizzasDocesService;

    @Autowired
    EstabelecimentoListarTodasPizzasSalgadasService estabelecimentoListarTodasPizzasSalgadasService;

    @Autowired
    EstabelecimentoListarTodasPizzasService estabelecimentoListarPizzaService;


    @GetMapping("/{id}/todasPizzasSalgadas")
    public ResponseEntity<?> buscarPizzasSalgadasEstabelecimento(
            @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoListarTodasPizzasSalgadasService.buscarTodasPizzasSalgadas(id));
    }

    @GetMapping("/{id}/todasPizzasDoces")
    public ResponseEntity<?> buscarPizzasDocesEstabelecimento(
            @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoListarTodasPizzasDocesService.buscarTodasPizzasDoces(id));
    }

    @GetMapping("/{id}/todasPizzas")
    public ResponseEntity<?> buscarTodasPizzasEstabelecimento(
            @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoListarPizzaService.buscarTodasPizzas(id));
    }
}
