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
    @Autowired
    EstabelecimentoAlterarParaIndisponivelPadraoService estabelecimentoAlterarParaIndisponivelService;
    @Autowired
    EstabelecimentoAlterarParaDisponivelPadraoService estabelecimentoAlterarParaDisponivelService;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUmEstabelecimento(
            @PathVariable Long id,
            @RequestBody @Valid EstabelecimentoGetRequestDTO estabelecimentoGetRequestDTO
            ) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(estabelecimentoGetService.get(id, estabelecimentoGetRequestDTO));
    }

    @GetMapping()
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
            @RequestBody @Valid EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoAlterarService.alterar(id, estabelecimentoPostPutRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirEstabelecimento(
            @PathVariable Long id,
            @RequestBody @Valid EstabelecimentoRemoveRequestDTO estabelecimentoRemoveRequestDTO
            ) {
        estabelecimentoExcluirService.excluir(id, estabelecimentoRemoveRequestDTO);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

    @DeleteMapping("/{idEstabelecimento}/lista-espera/{idFuncionario}")
    public ResponseEntity<?> removerEspera(
            @PathVariable Long idEstabelecimento,
            @PathVariable Long idFuncionario,
            @RequestBody @Valid EstabelecimentoRemoveRequestDTO estabelecimentoRemoveRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoRemoverEsperaService.excluirEspera(idEstabelecimento, idFuncionario, estabelecimentoRemoveRequestDTO));
    }

    @PostMapping ("/{id}/lista-espera/solicitacao")
    public ResponseEntity<?> solicitarPedido(
            @PathVariable Long id,
            @RequestBody @Valid FuncionarioSolicitaEntradaRequestDTO funcionarioSolicitaEntradaRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoSolicitarPedidoService.solicitarPedido(id, funcionarioSolicitaEntradaRequestDTO));
    }

    @PostMapping ("/{id}/lista-espera/aprovacao")
    public ResponseEntity<?> aceitarPedido(
            @PathVariable Long id,
            @RequestBody @Valid EstabelecimentoAceitarRequestDTO estabelecimentoAceitarRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoAceitarService.aceitar(estabelecimentoAceitarRequestDTO, id));
    }

    @PutMapping ("/{id}/indisponivel/{idPizza}")
    public ResponseEntity<?> mudarParaIndisponivel(
            @PathVariable Long id,
            @PathVariable Long idPizza
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoAlterarParaIndisponivelService.alterarDisponibilidade(idPizza, id));
    }

    @PutMapping ("/{id}/disponivel/{idPizza}")
    public ResponseEntity<?> mudarParaDisponivel(
            @PathVariable Long id,
            @PathVariable Long idPizza
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoAlterarParaDisponivelService.alterarDisponibilidade(idPizza, id));
    }
}
