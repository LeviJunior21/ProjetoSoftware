package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUmEstabelecimento(
            @PathVariable Long id) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(estabelecimentoGetService.get(id));
    }

    @GetMapping("")
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

    @PutMapping("/{id}/atualizar")
    public ResponseEntity<?> atualizarEstabelecimento(
            @PathVariable Long id,
            @RequestBody @Valid EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoAlterarService.alterar(id, estabelecimentoPostPutRequestDTO));
    }

    @DeleteMapping("/{id}/estabelecimento")
    public ResponseEntity<?> excluirEstabelecimento(
            @PathVariable Long id
        ) {
        estabelecimentoExcluirService.excluir(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

    @DeleteMapping("/{id}/remover_espera/{idFuncionario}")
    public ResponseEntity<?> removerEspera(
            @PathVariable Long id,
            @PathVariable Long idFuncionario
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoRemoverEsperaService.excluirEspera(id, idFuncionario));
    }

    @PutMapping ("/{id}/solicitar/{idEntregador}")
    public ResponseEntity<?> solicitarPedido(
            @PathVariable Long id,
            @PathVariable Long idEntregador
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoSolicitarPedidoService.solicitarPedido(id, idEntregador));
    }

    @PutMapping ("/{id}/aceitar/{idFuncionario}")
    public ResponseEntity<?> aceitarPedido(
            @PathVariable Long id,
            @PathVariable Long idFuncionario
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoAceitarService.aceitar(id, idFuncionario));
    }
}
