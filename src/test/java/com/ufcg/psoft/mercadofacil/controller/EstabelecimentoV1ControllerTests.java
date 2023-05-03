package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.EntregadorRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import com.ufcg.psoft.mercadofacil.service.estabelecimento.EstabelecimentoRemoverEntregadorService;
import com.ufcg.psoft.mercadofacil.service.estabelecimento.EstabelecimentoRemoverEsperaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.ufcg.psoft.mercadofacil.exception.CustomErrorType;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de Estabelecimentos")
public class EstabelecimentoV1ControllerTests {

    @Autowired
    MockMvc driver;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    Estabelecimento estabelecimento;
    Estabelecimento estabelecimento2;
    ObjectMapper objectMapper = new ObjectMapper();
    EstabelecimentoPostPutRequestDTO estabelecimentoPutRequestDTO;
    EstabelecimentoPostPutRequestDTO estabelecimentoPostRequestDTO;
    final String URI_ESTABELECIMENTOS = "/v1/estabelecimentos";

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        estabelecimento = estabelecimentoRepository.save(Estabelecimento.builder()
                .id(123456L)
                .nome("Sorveteria")
                .espera(new HashSet<Entregador>())
                .entregadores(new HashSet<>())
                .pizzas(new HashSet<>())
                .build()
        );
        estabelecimento2 = Estabelecimento.builder()
                .nome("Pizzando")
                .id(123489L)
                .build();
        estabelecimentoPostRequestDTO = EstabelecimentoPostPutRequestDTO.builder()
                .nome("Estabelecimento Dez")
                .id(123458L)
                .build();
        estabelecimentoPutRequestDTO = EstabelecimentoPostPutRequestDTO.builder()
                .nome("Estabelecimento Vinte")
                .id(123459L)
                .build();
    }

    @AfterEach
    void tearDown() {
        estabelecimentoRepository.deleteAll();
    }

    @Test
    @DisplayName("Quando criamos um novo produto com dados válidos")
    void quandoCriarEstabelecimentoValido() throws Exception {
        // Arrange
        // nenhuma necessidade além do setup()

        // Act
        String responseJsonString = driver.perform(post(URI_ESTABELECIMENTOS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estabelecimentoPostRequestDTO)))
                .andExpect(status().isCreated()) // Codigo 201
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        Estabelecimento resultado = objectMapper.readValue(responseJsonString, Estabelecimento.EstabelecimentoBuilder.class).build();

        // Assert
        assertAll(
                () -> assertNotNull(resultado.getId()),
                () -> assertEquals(estabelecimentoPostRequestDTO.getNome(), resultado.getNome())
        );

    }

    @Test
    @DisplayName("Quando alteramos um estabelecimento com nome válido")
    void quandoAlteramosUmEstabelecimentoValido() throws Exception {
        // Arrange
        EstabelecimentoNomePatchRequestDTO estabelecimentoNomePatchRequestDTO = EstabelecimentoNomePatchRequestDTO.builder()
                .nome("Padaria")
                .build();

        // Act
        String responseJsonString = driver.perform(patch("/v1/estabelecimentos/" + estabelecimento.getId() + "/nome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estabelecimentoNomePatchRequestDTO)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        Estabelecimento resultado = objectMapper.readValue(responseJsonString, Estabelecimento.EstabelecimentoBuilder.class).build();

        // Assert
        assertEquals(resultado.getNome(), "Padaria");
    }

    @Test
    @DisplayName("Quando alteramos apenas o nome do estabelecimento com dados inválido (em branco)")
    void quandoAlteramosNomeDoEstabelecimentoInvalidoBanco() throws Exception {
        // Arrange
        EstabelecimentoNomePatchRequestDTO estabelecimentoNomePatchRequestDTO = EstabelecimentoNomePatchRequestDTO.builder()
                .nome("")
                .build();

        // Act
        String responseJsonString = driver.perform(patch("/v1/estabelecimentos/" + estabelecimento.getId() + "/nome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estabelecimentoNomePatchRequestDTO)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

        // Assert
        assertEquals("Erros de validacao encontrados", resultado.getMessage());
        assertEquals("Nome obrigatorio", resultado.getErrors().get(0));
    }

    @Test
    @DisplayName("Quando alteramos apenas o id do estabelecimento com dados inválido (maior que 6 digitos)")
    void quandoCriamosUmEstabelecimentoComCodigoInvalidoMenorQueSeisDigitosBanco() throws Exception {
        // Arrange
        EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO = EstabelecimentoPostPutRequestDTO.builder()
                .nome("Estabelecimento A")
                .id(12345678L)
                .build();

        // Act
        String responseJsonString = driver.perform(post(URI_ESTABELECIMENTOS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estabelecimentoPostPutRequestDTO)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

        // Assert
        assertEquals("Erros de validacao encontrados", resultado.getMessage());
        assertEquals("O codigo deve ter 6 digitos", resultado.getErrors().get(0));
    }

    @Test
    @DisplayName("Quando alteramos apenas o id do estabelecimento com dados inválido (id null)")
    void quandoCriamosUmEstabelecimentoComCodigoInvalidoDigitosNullBanco() throws Exception {
        // Arrange
        EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO = EstabelecimentoPostPutRequestDTO.builder()
                .nome("Estabelecimento A")
                .id(null)
                .build();

        // Act
        String responseJsonString = driver.perform(post(URI_ESTABELECIMENTOS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estabelecimentoPostPutRequestDTO)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

        // Assert
        assertEquals("Erros de validacao encontrados", resultado.getMessage());
        assertEquals("O codigo deve ter 6 digitos", resultado.getErrors().get(0));
    }

    @Test
    @DisplayName("Quando alteramos apenas o id do estabelecimento com dados inválido (id menor que 6 digitos)")
    void quandoCriamosUmEstabelecimentoComCodigoInvalidoMaiorQueSeisDigitosBanco() throws Exception {
        // Arrange
        EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO = EstabelecimentoPostPutRequestDTO.builder()
                .nome("Estabelecimento A")
                .id(1234L)
                .build();

        // Act
        String responseJsonString = driver.perform(post(URI_ESTABELECIMENTOS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estabelecimentoPostPutRequestDTO)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

        // Assert
        assertEquals("Erros de validacao encontrados", resultado.getMessage());
        assertEquals("O codigo deve ter 6 digitos", resultado.getErrors().get(0));
    }

    @Nested
    @DisplayName("Casos de teste para ApiRestFull.")
    class CasosDeTesteApiRestFull {

        @Test
        @Transactional
        @DisplayName("Quando atualizamos um estabelecimento")
        void quandoAtualizamosUmEstabelecimento() throws Exception {
            // Arrange
            EstabelecimentoNomePatchRequestDTO estabelecimentoNomePatchRequestDTO = EstabelecimentoNomePatchRequestDTO.builder()
                    .nome("Pizzaria B")
                    .build();

            String responseJSONString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/atualizar")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(estabelecimentoNomePatchRequestDTO)))
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Estabelecimento estabelecimentoResponse = objectMapper.readValue(responseJSONString, Estabelecimento.EstabelecimentoBuilder.class).build();
            assertEquals("Pizzaria B", estabelecimentoResponse.getNome());
        }

        @Test
        @DisplayName("Quando excluimos o estabelecimento")
        void quandoExcluimosUmEstabelecimento() throws Exception {
            // Arrange
            String responseJsonString = driver.perform(delete(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/estabelecimento")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent()) // Codigo 204
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertTrue(responseJsonString.isBlank());
        }

        @Test
        @Transactional
        @DisplayName("Quando buscamos por todos estabelecimentos")
        void quandoBuscamosPorTodosEstabelecimentos() throws Exception {
            // Arrange
            // Act
            String responseJSONString = driver.perform(get(URI_ESTABELECIMENTOS)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            List<Estabelecimento> estabelecimentos = objectMapper.readValue(responseJSONString, new TypeReference<List<Estabelecimento>>() {});
            Estabelecimento estabelecimento1 = estabelecimentos.stream().findFirst().orElse(Estabelecimento.builder().build());

            //Assert
            assertEquals(1, estabelecimentos.size());
            assertAll(
                    () -> assertEquals(estabelecimento.getNome(), estabelecimento1.getNome()),
                    () -> assertEquals(estabelecimento.getId(), estabelecimento1.getId()),
                    () -> assertEquals(estabelecimento.getEspera().size(), estabelecimento1.getEspera().size()),
                    () -> assertEquals(estabelecimento.getEntregadores().size(), estabelecimento1.getEntregadores().size())
            );
        }

        @Test
        @DisplayName("Quando buscamos todos os estabelecimentos")
        void quandoBuscamosUmEstabelecimentos() throws Exception {
            // Arrange
            // Act
            String responseJSONString = driver.perform(get(URI_ESTABELECIMENTOS + "/" +estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            Estabelecimento estabelecimentoResultante = objectMapper.readValue(responseJSONString, Estabelecimento.EstabelecimentoBuilder.class).build();

            // Assert
            assertEquals(estabelecimento.getNome(), estabelecimentoResultante.getNome());
        }

        @Nested
        @DisplayName("Testes para aceitações dos pedidos no estabelecimento.")
        class TestePedidosAceitacoes {

            @Autowired
            EntregadorRepository entregadorRepository;
            Entregador entregador;
            EntregadorPostPutRequestDTO entregadorPostPutRequestDTO;

            @BeforeEach
            void setup2() {
                entregador = entregadorRepository.save(Entregador.builder()
                        .nome("Lucas")
                        .placa("131231")
                        .cor("vermelho")
                        .veiculo("moto")
                        .id(10L)
                        .build()
                );
                estabelecimento = estabelecimentoRepository.save(estabelecimento);
            }

            @Test
            @DisplayName("Quando um entregador solicita pedido para um estabelecimento")
            void quandoEntregadorSolicitaPedidoEstabelecimento() throws Exception {
                //Arrange
                //Act
                String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/solicitar/" + entregador.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()) // Codigo 200
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                Estabelecimento resultado = objectMapper.readValue(responseJsonString, Estabelecimento.EstabelecimentoBuilder.class).build();
                //Assert
                assertEquals(1, resultado.getEntregadores().size());
            }
        }

        @Nested
        @DisplayName("Casos de teste para remoção de entregadores aceitos ou não do estabelecimento")
        class CasosTesteRemocaoEntregador {

            @Autowired
            EstabelecimentoRemoverEntregadorService estabelecimentoRemoverEntregadorService;
            @Autowired
            EstabelecimentoRemoverEsperaService estabelecimentoRemoverEsperaService;
            @Autowired
            EntregadorRepository entregadorRepository;
            Entregador entregador;
            Entregador entregador2;

            @BeforeEach
            void setup() {

                entregador = entregadorRepository.save(Entregador.builder()
                        .nome("Lucas")
                        .placa("12344444")
                        .cor("vermelho")
                        .entregando(false)
                        .veiculo("carro")
                        .build()
                );

                entregador2 = entregadorRepository.save(Entregador.builder()
                        .nome("Lucas")
                        .placa("12344444")
                        .cor("vermelho")
                        .entregando(false)
                        .veiculo("carro")
                        .build()
                );

                estabelecimento.getEntregadores().add(entregador);
                estabelecimento.getEntregadores().add(entregador2);
                estabelecimentoRepository.save(estabelecimento);
            }

            @Test
            @Transactional
            @DisplayName("Quando remove um enregador da lista de espera")
            void quandoRemoveEntregadorEspera() throws Exception {
                // Arrange
                // Act

                String responseJSONString = driver.perform(delete(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/remover_espera/" + entregador.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                Estabelecimento response = objectMapper.readValue(responseJSONString, Estabelecimento.EstabelecimentoBuilder.class).build();
                // Assert
                assertEquals(0, response.getEspera().size());
            }

            @Test
            @Transactional
            @DisplayName("Quando aceita um entregador da lista de espera")
            void quandoRemoveEntregadorAceito() throws Exception {
                // Arrange
                // Act
                String responseJSONString = driver.perform(delete(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/remover_entregador/" + entregador.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                Estabelecimento response = objectMapper.readValue(responseJSONString, Estabelecimento.EstabelecimentoBuilder.class).build();
                // Assert
                assertEquals(2, response.getEntregadores().size());
            }
        }
    }
}
