package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.*;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
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
    @Autowired
    EstabelecimentoAceitarService estabelecimentoAceitarService;
    @Autowired
    EstabelecimentoSolicitarPedidoService estabelecimentoSolicitarPedidoService;
    @Autowired
    EstabelecimentoRemoverEsperaService estabelecimentoRemoverEsperaService;
    @Autowired
    EstabelecimentoGetService estabelecimentoGetService;

    @GetMapping("/estabelecimento")
    public ResponseEntity<?> buscarUmEstabelecimento(
            @RequestBody @Valid EstabelecimentoGetRequestDTO estabelecimentoGetRequestDTO
            ) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(estabelecimentoGetService.get(estabelecimentoGetRequestDTO));
    }

    @GetMapping("")
    public ResponseEntity<?> buscarTodosEstabelecimentos() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoListarService.listar());
    }

    @PostMapping()
    public ResponseEntity<?> salvarEstabelecimento(
            @RequestBody @Valid EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(estabelecimentoCriarService.salvar(estabelecimentoPostPutRequestDTO));
    }

    @PatchMapping("/alterar_nome")
    public ResponseEntity<?> atualizarParcialmenteEstabelecimento(
            @RequestBody @Valid EstabelecimentoNomePatchRequestDTO estabelecimentoNomePatchRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoAlterarNomeService.alterarParcialmente(estabelecimentoNomePatchRequestDTO));
    }

    @PutMapping("/{id}/atualizar")
    public ResponseEntity<?> atualizarEstabelecimento(
            @PathVariable Long id,
            @RequestBody @Valid EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoAlterarService.alterar(id, estabelecimentoPostPutRequestDTO));
    }

    @DeleteMapping("/estabelecimento")
    public ResponseEntity<?> excluirEstabelecimento(
            @RequestBody @Valid EstabelecimentoRemoveRequestDTO estabelecimentoRemoveRequestDTO
            ) {
        estabelecimentoExcluirService.excluir(estabelecimentoRemoveRequestDTO);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

    @DeleteMapping("/{id}/remover_espera")
    public ResponseEntity<?> removerEspera(
            @RequestBody @Valid EstabelecimentoRemoveRequestDTO estabelecimentoRemoveRequestDTO,
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoRemoverEsperaService.excluirEspera(estabelecimentoRemoveRequestDTO, id));
    }

    @PutMapping ("/{id}/solicitar")
    public ResponseEntity<?> solicitarPedido(
            @PathVariable Long id,
            @RequestBody @Valid FuncionarioSolicitaEntradaRequestDTO funcionarioSolicitaEntradaRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoSolicitarPedidoService.solicitarPedido(id, funcionarioSolicitaEntradaRequestDTO));
    }

    @PutMapping ("/{id}/aceitar")
    public ResponseEntity<?> aceitarPedido(
            @PathVariable Long id,
            @RequestBody @Valid EstabelecimentoAceitarRequestDTO estabelecimentoAceitarRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoAceitarService.aceitar(estabelecimentoAceitarRequestDTO, id));
    }
}
