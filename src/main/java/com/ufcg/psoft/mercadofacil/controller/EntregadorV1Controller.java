package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostPutRequestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ufcg.psoft.mercadofacil.service.entregador.*;

@RestController
@RequestMapping(
        value = "/v1/entregadores",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class EntregadorV1Controller {
    @Autowired
    EntregadorListarService entregadorListarService;
    @Autowired
    EntregadorCriarService entregadorCriarService;
    @Autowired
    EntregadorAlterarService entregadorAlterarService;
    @Autowired
    EntregadorExcluirService entregadorExcluirService;
    @Autowired
    EntregadorAlterarNomeService entregadorAlterarNomeService;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUmEntregador(
            @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorListarService.listar(id));
    }

    @GetMapping("")
    public ResponseEntity<?> buscarTodosEntregadores() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorListarService.listar(null));
    }

    @PostMapping()
    public ResponseEntity<?> salvarEntregador(
            @RequestBody @Valid EntregadorPostPutRequestDTO entregadorPostPutRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entregadorCriarService.salvar(entregadorPostPutRequestDTO));
    }

    @PatchMapping("/{id}/nome")
    public ResponseEntity<?> atualizarParcialmenteEntregador(
            @PathVariable Long id,
            @RequestBody @Valid EntregadorNomePatchRequestDTO entregadorNomePatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorAlterarNomeService.alterarParcialmente(id, entregadorNomePatchRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEntregador(
            @PathVariable Long idEntregador,
            @RequestBody @Valid EntregadorPostPutRequestDTO entregadorPostPutRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorAlterarService.alterar(idEntregador, entregadorPostPutRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirEntregador(
            @PathVariable Long id
    ) {
        entregadorExcluirService.excluir(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }
}
