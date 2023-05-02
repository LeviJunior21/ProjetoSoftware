package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.mercadofacil.dto.lote.LoteItensPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.lote.LoteNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.lote.LotePostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.LoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.ufcg.psoft.mercadofacil.exception.CustomErrorType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de lote")
public class LoteV1ControllerTests {

    @Autowired
    LoteRepository loteRepository;

    @Autowired
    MockMvc driver;

    ObjectMapper objectMapper = new ObjectMapper();

    private static final String URI_LOTES = "/v1/lotes";
    Lote lote;
    Produto produto;
    LotePostPutRequestDTO lotePostPutRequestDTO;
    LoteItensPatchRequestDTO loteItensPatchRequestDTO;
    LoteNomePatchRequestDTO loteNomePatchRequestDTO;

    @BeforeEach
    void setup() {
        produto = Produto.builder()
                .nome("Pizza Calabresa")
                .id(1L)
                .fabricante("Fabricante Dez")
                .codigoDeBarras("1234567891234")
                .preco(30.00)
                .build();
        lote = loteRepository.save(Lote.builder()
                .numeroDeItens(10)
                .id(2L)
                .produto(produto)
                .build()
        );
    }

    @Nested
    @DisplayName("Classe de testes de atualizaçõe do lote")
    class AtualizaInformacoesLote {
        @Test
        @DisplayName("Quando alteramos o nome do lote")
        void quandoAlteramosPrecoLoteInvalido() throws Exception {
            // Arrange
            LoteItensPatchRequestDTO loteItensPatchRequestDTO = LoteItensPatchRequestDTO.builder()
                    .numeroDeItens(0)
                    .build();

            // Act
            String responseJsonString = driver.perform(patch(URI_LOTES + produto.getId() + "/itens")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loteItensPatchRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("Nome obrigatorio", resultado.getErrors().get(0));
        }
    }
}
