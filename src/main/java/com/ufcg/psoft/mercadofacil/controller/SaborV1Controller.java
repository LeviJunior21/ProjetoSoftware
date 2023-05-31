package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.sabor.SaborNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.sabor.SaborPrecoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.sabor.SaborTipoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.sabor.*;
import com.ufcg.psoft.mercadofacil.service.sabor.SaborAlterarNomeService;
import com.ufcg.psoft.mercadofacil.service.sabor.SaborAlterarPrecoService;
import com.ufcg.psoft.mercadofacil.service.sabor.SaborAlterarTipoService;
import com.ufcg.psoft.mercadofacil.service.sabor.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/v1/sabores",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class SaborV1Controller {

    @Autowired
    SaborListarService saborListarService;
    @Autowired
    SaborCriarPadraoService saborCriarService;
    @Autowired
    SaborAlterarService saborAtualizarService;
    @Autowired
    SaborExcluirService saborExcluirService;
    @Autowired
    SaborAlterarNomeService saborAlterarNomeService;
    @Autowired
    SaborAlterarPrecoService saborAlterarPrecoService;
    @Autowired
    SaborAlterarTipoService saborAlterarTipoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUmSabor(
            @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(saborListarService.listar(id));
    }

    @GetMapping("")
    public ResponseEntity<?> buscarTodosSabores() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(saborListarService.listar(null));
    }

    @PostMapping()
    public ResponseEntity<?> salvarSabor(
            @RequestBody @Valid SaborPostPutRequestDTO saborPostPutRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saborCriarService.salvar(saborPostPutRequestDto));
    }

    @PatchMapping("/{id}/nome")
    public ResponseEntity<?> atualizarParcialmenteSaborNome(
            @PathVariable Long id,
            @RequestBody @Valid SaborNomePatchRequestDTO saborNomePatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(saborAlterarNomeService.alterarParcialmente(id, saborNomePatchRequestDTO));
    }

    @PatchMapping("/{id}/preco")
    public ResponseEntity<?> atualizarParcialmenteSaborPreco(
            @PathVariable Long id,
            @RequestBody @Valid SaborPrecoPatchRequestDTO saborPrecoPatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(saborAlterarPrecoService.alterarParcialmente(id, saborPrecoPatchRequestDTO));
    }

    @PatchMapping("/{id}/tipo")
    public ResponseEntity<?> atualizarParcialmenteSaborTipo(
            @PathVariable Long id,
            @RequestBody @Valid SaborTipoPatchRequestDTO saborTipoPatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(saborAlterarTipoService.alterarParcialmente(id, saborTipoPatchRequestDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarSabor(
            @PathVariable Long id,
            @RequestBody @Valid SaborPostPutRequestDTO saborPostPutRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(saborAtualizarService.alterar(id, saborPostPutRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirSabor(
            @PathVariable Long id
    ) {
        saborExcluirService.excluir(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

}