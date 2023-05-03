package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.entregador.*;
import com.ufcg.psoft.mercadofacil.service.entregador.EntregadorAlterarCorService;
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

    @Autowired
    EntregadorAlterarCorService entregadorAlterarCorService;

    @Autowired
    EntregadorAlterarPlacaService entregadorAlterarPlacaService;

    @Autowired
    EntregadorAlterarVeiculoService entregadorAlterarVeiculoService;

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
    public ResponseEntity<?> atualizarParcialmenteEntregadorNome(
            @PathVariable Long id,
            @RequestBody @Valid EntregadorNomePatchRequestDTO entregadorNomePatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorAlterarNomeService.alterarParcialmente(id, entregadorNomePatchRequestDTO));
    }

    @PatchMapping("/{id}/cor")
    public ResponseEntity<?> atualizarParcialmenteEntregadorCor(
            @PathVariable Long id,
            @RequestBody @Valid EntregadorCorPatchRequestDTO entregadorCorPatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorAlterarCorService.alterarParcialmente(id, entregadorCorPatchRequestDTO));
    }

    @PatchMapping("/{id}/placa")
    public ResponseEntity<?> atualizarParcialmenteEntregadorPlaca(
            @PathVariable Long id,
            @RequestBody @Valid EntregadorPlacaPatchRequestDTO entregadorPlacaPatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorAlterarPlacaService.alterarParcialmente(id, entregadorPlacaPatchRequestDTO));
    }

    @PatchMapping("/{id}/veiculo")
    public ResponseEntity<?> atualizarParcialmenteEntregadorVeiculo(
            @PathVariable Long id,
            @RequestBody @Valid EntregadorVeiculoPatchRequestDTO entregadorVeiculoPatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorAlterarVeiculoService.alterarParcialmente(id, entregadorVeiculoPatchRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEntregador(
            @PathVariable Long id,
            @RequestBody @Valid EntregadorPostPutRequestDTO entregadorPostPutRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorAlterarService.alterar(id, entregadorPostPutRequestDto));
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
