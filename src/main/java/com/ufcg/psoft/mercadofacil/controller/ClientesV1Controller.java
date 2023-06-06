package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.cliente.*;
import com.ufcg.psoft.mercadofacil.service.cliente.*;
import com.ufcg.psoft.mercadofacil.service.cliente.ClienteSolicitarPedidoService;
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
    @Autowired
    ClienteSolicitarPedidoService clienteSolicitarPedidoService;
    @Autowired
    ClienteInteressePizzaService clienteInteressePizzaService;
    @Autowired
    ClienteAlterarStateParaEntregueService clienteAlterarStateParaEntregueService;
    @Autowired
    ClienteAlterarParaEntregueService clienteAlterarParaEntregueService;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUmCliente(
            @PathVariable Long id,
            @RequestBody @Valid ClienteGetRequestDTO clienteGetRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteBuscarService.get(id, clienteGetRequestDTO));
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
            @PathVariable Long id,
            @RequestBody @Valid ClienteNomePatchRequestDTO clienteNomePatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteAlterarNomeService.alterarParcialmente(id, clienteNomePatchRequestDTO));
    }

    @PatchMapping("/{id}/endereco")
    public ResponseEntity<?> atualizarParcialmenteClienteEndereco(
            @PathVariable Long id,
            @RequestBody @Valid ClienteEnderecoPatchRequestDTO clienteEnderecoPatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteAlterarEnderecoService.alterarParcialmente(id, clienteEnderecoPatchRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCliente(
            @PathVariable Long id,
            @RequestBody @Valid ClientePostPutRequestDTO clientePostPutRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteAlterarService.alterar(id, clientePostPutRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirCliente(
            @PathVariable Long id,
            @RequestBody @Valid ClienteRemoveRequestDTO clienteRemoveRequestDTO
    ) {
        clienteExcluirService.excluir(id, clienteRemoveRequestDTO);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

    @PostMapping("/{idCliente}/solicitar-pedido/{idEstabelecimento}")
    public ResponseEntity<?> solicitarPedido (
            @PathVariable Long idCliente,
            @PathVariable Long idEstabelecimento,
            @RequestBody @Valid ClientePedidoRequestDTO clientePedidoRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteSolicitarPedidoService.solicitar(idCliente, idEstabelecimento, clientePedidoRequestDTO));
    }

    @PatchMapping("/{idCliente}/interessar_pizza/{idEstabelecimento}/{idPizza}")
    public ResponseEntity<?> interessarPizza(
            @PathVariable Long idCliente,
            @PathVariable Long idEstabelecimento,
            @PathVariable Long idPizza
    ) {
        clienteInteressePizzaService.interessar(idCliente, idEstabelecimento, idPizza);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("");
    }

    @PatchMapping("/{idCliente}/desinteressar_pizza/{idEstabelecimento}/{idPizza}")
    public ResponseEntity<?> desinteressarPizza(
            @PathVariable Long idCliente,
            @PathVariable Long idEstabelecimento,
            @PathVariable Long idPizza
    ) {
        clienteInteressePizzaService.desinteressar(idCliente, idEstabelecimento, idPizza);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("");
    }

    @PatchMapping("/{idCliente}/alterar-state-pedido/{idEstabelecimento}")
    public ResponseEntity<?> alteraStatePedido(
            @PathVariable Long idCliente,
            @PathVariable Long idEstabelecimento,
            @RequestBody @Valid ClientePedidoPatchRequestDTO clientePedidoPatchRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteAlterarStateParaEntregueService.alterarPedidoState(idCliente, idEstabelecimento, clientePedidoPatchRequestDTO));
    }

    @PostMapping("{idCliente}/entregue/{idEstabelecimento}/{idPedido}")
    public ResponseEntity<?> alteraParaEntregue(
            @PathVariable Long idCliente,
            @PathVariable Long idEstabelecimento,
            @PathVariable Long idPedido,
            @RequestBody @Valid ClientePedidoPostDTO clientePedidoPostDTO
    ) {
        clienteAlterarParaEntregueService.alterarParaEntregue(idCliente, idEstabelecimento, idPedido, clientePedidoPostDTO);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }
}
