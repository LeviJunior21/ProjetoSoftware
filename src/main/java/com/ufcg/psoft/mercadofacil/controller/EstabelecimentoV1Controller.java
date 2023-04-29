package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostPutRequestDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUmEstabelecimento(
            @PathVariable Long id) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(estabelecimentoListarService.listar(id));
    }

    @GetMapping("")
    public ResponseEntity<?> buscarTodosEstabelecimentos() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoListarService.listar(null));
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
            @RequestBody @Valid EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoAlterarService.alterar(id, estabelecimentoPostPutRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirEstabelecimento(
            @PathVariable Long id
        ) {
        estabelecimentoExcluirService.excluir(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }
}
