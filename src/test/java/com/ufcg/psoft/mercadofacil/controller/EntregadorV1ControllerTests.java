package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.repository.EntregadorRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Classe de testes do controlador de entregador")
public class EntregadorV1ControllerTests {

    @Autowired
    MockMvc driver;

    @Autowired
    EntregadorRepository entregadorRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    Entregador entregador;
    @BeforeEach
    void setup() {
        entregador = entregadorRepository.save(
                Entregador.builder()
                        .cor("prata")
                        .nome("Lucas")
                        .veiculo("Fuskinha")
                        .placa("20103")
                        .entregando(false)
                        .build()
        );
    }

    @AfterEach
    void tearDown() {
        entregadorRepository.deleteAll();
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de campos obrigatórios")
    class EntregadorVerificacaoCamposObrigatorios {

        @Test
        @DisplayName("Quando alteramos o nome do entregador com dados válidos")
        void quandoAlteramosNomeDoEntregadorValido() throws Exception {
            // Arrange
            EntregadorNomePatchRequestDTO entregadorNomePatchRequestDTO = EntregadorNomePatchRequestDTO.builder()
                    .nome("levi")
                    .build();

            // Act
            String responseJsonString = driver.perform(patch("/v1/entregadores/" + entregador.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorNomePatchRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Entregador resultado = objectMapper.readValue(responseJsonString, Entregador.EntregadorBuilder.class).build();

            // Assert
            assertEquals(entregadorNomePatchRequestDTO.getNome(), resultado.getNome());
        }

    }
}
