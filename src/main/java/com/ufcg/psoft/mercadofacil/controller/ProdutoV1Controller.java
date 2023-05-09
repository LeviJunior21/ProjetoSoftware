package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.produto.ProdutoNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.produto.ProdutoPrecoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.produto.ProdutoTamanhoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.produto.ProdutoTipoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.produto.*;
import com.ufcg.psoft.mercadofacil.service.produto.ProdutoAlterarNomeService;
import com.ufcg.psoft.mercadofacil.service.produto.ProdutoAlterarPrecoService;
import com.ufcg.psoft.mercadofacil.service.produto.ProdutoAlterarTamanhoService;
import com.ufcg.psoft.mercadofacil.service.produto.ProdutoAlterarTipoService;
import com.ufcg.psoft.mercadofacil.service.produto.*;
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
    @Autowired
    ProdutoAlterarNomeService produtoAlterarNomeService;
    @Autowired
    ProdutoAlterarPrecoService produtoAlterarPrecoService;
    @Autowired
    ProdutoAlterarTamanhoService produtoAlterarTamanhoService;
    @Autowired
    ProdutoAlterarTipoService produtoAlterarTipoService;

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

    @PatchMapping("/{id}/nome")
    public ResponseEntity<?> atualizarParcialmenteProdutoNome(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoNomePatchRequestDTO produtoNomePatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(produtoAlterarNomeService.alterarParcialmente(id, produtoNomePatchRequestDTO));
    }

    @PatchMapping("/{id}/preco")
    public ResponseEntity<?> atualizarParcialmenteProdutoPreco(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoPrecoPatchRequestDTO produtoPrecoPatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(produtoAlterarPrecoService.alterarParcialmente(id, produtoPrecoPatchRequestDTO));
    }

    @PatchMapping("/{id}/tamanho")
    public ResponseEntity<?> atualizarParcialmenteProdutoTamanho(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoTamanhoPatchRequestDTO produtoTamanhoPatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(produtoAlterarTamanhoService.alterarParcialmente(id, produtoTamanhoPatchRequestDTO));
    }

    @PatchMapping("/{id}/tipo")
    public ResponseEntity<?> atualizarParcialmenteProdutoTipo(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoTipoPatchRequestDTO produtoTipoPatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(produtoAlterarTipoService.alterarParcialmente(id, produtoTipoPatchRequestDTO));
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