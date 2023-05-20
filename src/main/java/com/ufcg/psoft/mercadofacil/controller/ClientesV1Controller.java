package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.cliente.*;
import com.ufcg.psoft.mercadofacil.service.cliente.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/v1/clientes",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ClientesV1Controller {

    @Autowired
    ClienteListarService clienteListarService;
    @Autowired
    ClienteCriarService clienteCriarService;
    @Autowired
    ClienteAlterarNomeService clienteAlterarNomeService;
    @Autowired
    ClienteAlterarEnderecoService clienteAlterarEnderecoService;
    @Autowired
    ClienteAlterarService clienteAlterarService;
    @Autowired
    ClienteBuscarService clienteBuscarService;
    @Autowired
    ClienteExcluirService clienteExcluirService;

    @GetMapping("/cliente")
    public ResponseEntity<?> buscarUmCliente(
            @RequestBody @Valid ClienteGetRequestDTO clienteGetRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteBuscarService.get(clienteGetRequestDTO));
    }

    @GetMapping("")
    public ResponseEntity<?> buscarTodosClientes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteListarService.listar());
    }

    @PostMapping()
    public ResponseEntity<?> salvarCliente(
            @RequestBody @Valid ClientePostPutRequestDTO clientePostPutRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clienteCriarService.salvar(clientePostPutRequestDTO));
    }

    @PatchMapping("/{id}/nome")
    public ResponseEntity<?> atualizarParcialmenteClienteNome(
            @RequestBody @Valid ClienteNomePatchRequestDTO clienteNomePatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteAlterarNomeService.alterarParcialmente(clienteNomePatchRequestDTO));
    }

    @PatchMapping("/endereco")
    public ResponseEntity<?> atualizarParcialmenteClienteEndereco(
            @RequestBody @Valid ClienteEnderecoPatchRequestDTO clienteEnderecoPatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteAlterarEnderecoService.alterarParcialmente(clienteEnderecoPatchRequestDTO));
    }

    @PutMapping("/cliente")
    public ResponseEntity<?> atualizarCliente(
            @RequestBody @Valid ClientePostPutRequestDTO clientePostPutRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteAlterarService.alterar(clientePostPutRequestDTO));
    }

    @DeleteMapping("/cliente")
    public ResponseEntity<?> excluirCliente(
            @RequestBody @Valid ClienteRemoveRequestDTO clienteRemoveRequestDTO
    ) {
        clienteExcluirService.excluir(clienteRemoveRequestDTO);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

}
