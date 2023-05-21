package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.*;
import com.ufcg.psoft.mercadofacil.dto.pizza.PizzaGetRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.pizza.PizzaRemoveRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.pizza.SaborPizzaPostPutRequestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ufcg.psoft.mercadofacil.service.estabelecimento.*;

@RestController
@RequestMapping(
        value = "/v1/estabelecimentos",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class EstabelecimentoV1Controller {

    @Autowired
    EstabelecimentoListarService estabelecimentoListarService;
    @Autowired
    EstabelecimentoCriarService estabelecimentoCriarService;
    @Autowired
    EstabelecimentoAlterarService estabelecimentoAlterarService;
    @Autowired
    EstabelecimentoExcluirService estabelecimentoExcluirService;
    @Autowired
    EstabelecimentoAlterarNomeService estabelecimentoAlterarNomeService;
    @Autowired
    EstabelecimentoAceitarService estabelecimentoAceitarService;
    @Autowired
    EstabelecimentoSolicitarPedidoService estabelecimentoSolicitarPedidoService;
    @Autowired
    EstabelecimentoRemoverEsperaService estabelecimentoRemoverEsperaService;
    @Autowired
    EstabelecimentoGetService estabelecimentoGetService;
    @Autowired
    EstabelecimentoAlterarParaIndisponivelPadraoService estabelecimentoAlterarParaIndisponivelService;
    @Autowired
    EstabelecimentoAlterarParaDisponivelPadraoService estabelecimentoAlterarParaDisponivelService;
    @Autowired
    EstabelecimentoPizzaService estabelecimentoPizzaService;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUmEstabelecimento(
            @PathVariable Long id,
            @RequestBody @Valid EstabelecimentoGetRequestDTO estabelecimentoGetRequestDTO
            ) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(estabelecimentoGetService.get(id, estabelecimentoGetRequestDTO));
    }

    @GetMapping()
    public ResponseEntity<?> buscarTodosEstabelecimentos() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoListarService.listar());
    }

    @PostMapping()
    public ResponseEntity<?> salvarEstabelecimento(
            @RequestBody @Valid EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(estabelecimentoCriarService.salvar(estabelecimentoPostPutRequestDTO));
    }

    @PatchMapping("/{id}/nome")
    public ResponseEntity<?> atualizarParcialmenteEstabelecimento(
            @PathVariable Long id,
            @RequestBody @Valid EstabelecimentoNomePatchRequestDTO estabelecimentoNomePatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoAlterarNomeService.alterarParcialmente(id, estabelecimentoNomePatchRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEstabelecimento(
            @PathVariable Long id,
            @RequestBody @Valid EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoAlterarService.alterar(id, estabelecimentoPostPutRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirEstabelecimento(
            @PathVariable Long id,
            @RequestBody @Valid EstabelecimentoRemoveRequestDTO estabelecimentoRemoveRequestDTO
            ) {
        estabelecimentoExcluirService.excluir(id, estabelecimentoRemoveRequestDTO);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

    @DeleteMapping("/{idEstabelecimento}/lista-espera/{idFuncionario}")
    public ResponseEntity<?> removerEspera(
            @PathVariable Long idEstabelecimento,
            @PathVariable Long idFuncionario,
            @RequestBody @Valid EstabelecimentoRemoveRequestDTO estabelecimentoRemoveRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoRemoverEsperaService.excluirEspera(idEstabelecimento, idFuncionario, estabelecimentoRemoveRequestDTO));
    }

    @PostMapping ("/{id}/lista-espera/solicitacao")
    public ResponseEntity<?> solicitarPedido(
            @PathVariable Long id,
            @RequestBody @Valid FuncionarioSolicitaEntradaPostRequestDTO funcionarioSolicitaEntradaPostRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoSolicitarPedidoService.solicitarPedido(id, funcionarioSolicitaEntradaPostRequestDTO));
    }

    @PostMapping ("/{idEstabelecimento}/lista-espera/{idFuncionario}/aprovacao")
    public ResponseEntity<?> aceitarPedido(
            @PathVariable Long idEstabelecimento,
            @PathVariable Long idFuncionario,
            @RequestBody @Valid EstabelecimentoAceitarPostRequestDTO estabelecimentoAceitarPostRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoAceitarService.aceitar(idEstabelecimento, idFuncionario, estabelecimentoAceitarPostRequestDTO));
    }

    @PutMapping ("/{id}/indisponivel/{idPizza}")
    public ResponseEntity<?> mudarParaIndisponivel(
            @PathVariable Long id,
            @PathVariable Long idPizza
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoAlterarParaIndisponivelService.alterarDisponibilidade(idPizza, id));
    }

    @PutMapping ("/{id}/disponivel/{idPizza}")
    public ResponseEntity<?> mudarParaDisponivel(
            @PathVariable Long id,
            @PathVariable Long idPizza
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoAlterarParaDisponivelService.alterarDisponibilidade(idPizza, id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> salvarPizza(
            @PathVariable Long id,
            @RequestBody @Valid SaborPizzaPostPutRequestDTO saborPizzaPostPutRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(estabelecimentoPizzaService.salvar(id, saborPizzaPostPutRequestDTO));
    }

    @PutMapping("/{id}/atualizar_pizza/{idPizza}")
    public ResponseEntity<?> atualizarPizza(
            @PathVariable Long id,
            @PathVariable Long idPizza,
            @RequestBody @Valid SaborPizzaPostPutRequestDTO saborPizzaPostPutRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoPizzaService.alterar(id, idPizza, saborPizzaPostPutRequestDTO));
    }

    @DeleteMapping("/{id}/remove_pizza")
    public ResponseEntity<?> removerPizza(
            @PathVariable Long id,
            @RequestBody @Valid PizzaRemoveRequestDTO pizzaRemoveRequestDTO
    ) {
        estabelecimentoPizzaService.excluir(id, pizzaRemoveRequestDTO);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

    @GetMapping("/{id}/cardapio")
    public ResponseEntity<?> buscarUmaPizza(
            @PathVariable Long id,
            @RequestBody @Valid PizzaGetRequestDTO pizzaGetRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoPizzaService.get(id, pizzaGetRequestDTO));
    }

    @GetMapping("/{id}/lista_cardapio")
    public ResponseEntity<?> listarCardapio(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoPizzaService.listar(id));
    }
}
