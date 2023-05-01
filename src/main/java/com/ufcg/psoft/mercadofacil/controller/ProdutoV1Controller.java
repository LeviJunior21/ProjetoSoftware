package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.produto.ProdutoPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.service.produto.ProdutoAlterarService;
import com.ufcg.psoft.mercadofacil.service.produto.ProdutoCriarPadraoService;
import com.ufcg.psoft.mercadofacil.service.produto.ProdutoExcluirService;
import com.ufcg.psoft.mercadofacil.service.produto.ProdutoListarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/v1/produtos",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ProdutoV1Controller {

    @Autowired
    ProdutoListarService produtoListarService;
    @Autowired
    ProdutoCriarPadraoService produtoCriarService;
    @Autowired
    ProdutoAlterarService produtoAtualizarService;
    @Autowired
    ProdutoExcluirService produtoExcluirService;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUmProduto(
            @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(produtoListarService.listar(id));
    }

    @GetMapping("")
    public ResponseEntity<?> buscarTodosProdutos() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(produtoListarService.listar(null));
    }

    @PostMapping()
    public ResponseEntity<?> salvarProduto(
            @RequestBody @Valid ProdutoPostPutRequestDTO produtoPostPutRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(produtoCriarService.salvar(produtoPostPutRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProduto(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoPostPutRequestDTO produtoPostPutRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(produtoAtualizarService.alterar(id, produtoPostPutRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirProduto(
            @PathVariable Long id
    ) {
        produtoExcluirService.excluir(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

}
