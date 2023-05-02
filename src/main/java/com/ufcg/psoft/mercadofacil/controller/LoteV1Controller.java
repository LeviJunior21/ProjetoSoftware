package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.lote.LotePostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.service.lote.LoteAlterarService;
import com.ufcg.psoft.mercadofacil.service.lote.LoteCriarPadraoService;
import com.ufcg.psoft.mercadofacil.service.lote.LoteExcluirService;
import com.ufcg.psoft.mercadofacil.service.lote.LoteListarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/v1/lotes",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class LoteV1Controller {

    @Autowired
    LoteListarService loteListarService;
    @Autowired
    LoteCriarPadraoService loteCriarService;
    @Autowired
    LoteAlterarService loteAtualizarService;
    @Autowired
    LoteExcluirService loteExcluirService;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUmLote(
            @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loteListarService.listar(id));
    }

    @GetMapping("")
    public ResponseEntity<?> buscarTodosLotes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loteListarService.listar(null));
    }

    @PostMapping()
    public ResponseEntity<?> salvarLote(
            @RequestBody @Valid LotePostPutRequestDTO lotePostPutRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(loteCriarService.salvar(lotePostPutRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarLote(
            @PathVariable Long id,
            @RequestBody @Valid LotePostPutRequestDTO lotePostPutRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loteAtualizarService.alterar(id, lotePostPutRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirLote(
            @PathVariable Long id
    ) {
        loteExcluirService.excluir(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }
}
