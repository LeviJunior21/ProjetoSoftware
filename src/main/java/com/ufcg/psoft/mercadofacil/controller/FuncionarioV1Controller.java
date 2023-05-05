package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.funcionario.*;
import com.ufcg.psoft.mercadofacil.service.funcionario.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/v1/funcionarios",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class FuncionarioV1Controller {
    @Autowired
    FuncionarioListarService funcionarioListarService;
    @Autowired
    FuncionarioCriarService funcionarioCriarService;
    @Autowired
    FuncionarioAlterarService funcionarioAlterarService;
    @Autowired
    FuncionarioExcluirService funcionarioExcluirService;
    @Autowired
    FuncionarioAlterarNomeService funcionarioAlterarNomeService;

    @Autowired
    FuncionarioAlterarCorService funcionarioAlterarCorService;

    @Autowired
    FuncionarioAlterarPlacaService funcionarioAlterarPlacaService;

    @Autowired
    FuncionarioAlterarVeiculoService funcionarioAlterarVeiculoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUmFuncionario(
            @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(funcionarioListarService.listar(id));
    }

    @GetMapping("")
    public ResponseEntity<?> buscarTodosFuncionarios() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(funcionarioListarService.listar(null));
    }

    @PostMapping()
    public ResponseEntity<?> salvarFuncionario(
            @RequestBody @Valid FuncionarioPostPutRequestDTO funcionarioPostPutRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(funcionarioCriarService.salvar(funcionarioPostPutRequestDTO));
    }

    @PatchMapping("/{id}/nome")
    public ResponseEntity<?> atualizarParcialmenteFuncionarioNome(
            @PathVariable Long id,
            @RequestBody @Valid FuncionarioNomePatchRequestDTO funcionarioNomePatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(funcionarioAlterarNomeService.alterarParcialmente(id, funcionarioNomePatchRequestDTO));
    }

    @PatchMapping("/{id}/cor")
    public ResponseEntity<?> atualizarParcialmenteFuncionarioCor(
            @PathVariable Long id,
            @RequestBody @Valid FuncionarioCorPatchRequestDTO funcionarioCorPatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(funcionarioAlterarCorService.alterarParcialmente(id, funcionarioCorPatchRequestDTO));
    }

    @PatchMapping("/{id}/placa")
    public ResponseEntity<?> atualizarParcialmenteFuncionarioPlaca(
            @PathVariable Long id,
            @RequestBody @Valid FuncionarioPlacaPatchRequestDTO funcionarioPlacaPatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(funcionarioAlterarPlacaService.alterarParcialmente(id, funcionarioPlacaPatchRequestDTO));
    }

    @PatchMapping("/{id}/veiculo")
    public ResponseEntity<?> atualizarParcialmenteFuncionarioVeiculo(
            @PathVariable Long id,
            @RequestBody @Valid FuncionarioVeiculoPatchRequestDTO funcionarioVeiculoPatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(funcionarioAlterarVeiculoService.alterarParcialmente(id, funcionarioVeiculoPatchRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarFuncionario(
            @PathVariable Long id,
            @RequestBody @Valid FuncionarioPostPutRequestDTO funcionarioPostPutRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(funcionarioAlterarService.alterar(id, funcionarioPostPutRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirEntregador(
            @PathVariable Long id
    ) {
        funcionarioExcluirService.excluir(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }
}
