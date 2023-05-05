package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.mercadofacil.dto.entregador.*;
import com.ufcg.psoft.mercadofacil.exception.CustomErrorType;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@DisplayName("Classe de testes do controlador de entregador")
public class FuncionarioV1ControllerTests {

    @Autowired
    MockMvc driver;

    @Autowired
    FuncionarioRepository entregadorRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    Entregador entregador;
    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());
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
    @DisplayName("Conjunto de casos de verificação de regras sobre o nome")
    class EntregadorVerificacaoNome {
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

        @Test
        @DisplayName("Quando alteramos apenas o nome do entregador com dados inválidos (em branco)")
        void quandoAlteramosNomeDoEntregadorInvalidoBanco() throws Exception {
            // Arrange
            EntregadorNomePatchRequestDTO entregadorNomePatchRequestDTO = EntregadorNomePatchRequestDTO.builder()
                    .nome("")
                    .build();

            // Act
            String responseJsonString = driver.perform(patch("/v1/entregadores/" + entregador.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorNomePatchRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("Nome obrigatorio", resultado.getErrors().get(0));
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de regras sobre a cor")
    class EntregadorVerificacaoCor {
        @Test
        @DisplayName("Quando alteramos a cor do entregador com dados válidos")
        void quandoAlteramosCorDoEntregadorValido() throws Exception {
            // Arrange
            EntregadorCorPatchRequestDTO entregadorCorPatchRequestDTO = EntregadorCorPatchRequestDTO.builder()
                    .cor("amarelo")
                    .build();

            // Act
            String responseJsonString = driver.perform(patch("/v1/entregadores/" + entregador.getId() + "/cor")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorCorPatchRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Entregador resultado = objectMapper.readValue(responseJsonString, Entregador.EntregadorBuilder.class).build();

            // Assert
            assertEquals(entregadorCorPatchRequestDTO.getCor(), resultado.getCor());
        }

        @Test
        @DisplayName("Quando alteramos apenas a cor do entregador com dados inválidos (em branco)")
        void quandoAlteramosCorDoEntregadorInvalidoBanco() throws Exception {
            // Arrange
            EntregadorCorPatchRequestDTO entregadorCorPatchRequestDTO = EntregadorCorPatchRequestDTO.builder()
                    .cor("")
                    .build();

            // Act
            String responseJsonString = driver.perform(patch("/v1/entregadores/" + entregador.getId() + "/cor")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorCorPatchRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("Cor do carro obrigatorio", resultado.getErrors().get(0));
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de regras sobre a placa")
    class EntregadorVerificacaoPlaca {
        @Test
        @DisplayName("Quando alteramos a placa do entregador com dados válidos")
        void quandoAlteramosPlacaDoEntregadorValido() throws Exception {
            // Arrange
            EntregadorPlacaPatchRequestDTO entregadorPlacaPatchRequestDTO = EntregadorPlacaPatchRequestDTO.builder()
                    .placa("5atfa4")
                    .build();

            // Act
            String responseJsonString = driver.perform(patch("/v1/entregadores/" + entregador.getId() + "/placa")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPlacaPatchRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Entregador resultado = objectMapper.readValue(responseJsonString, Entregador.EntregadorBuilder.class).build();

            // Assert
            assertEquals(entregadorPlacaPatchRequestDTO.getPlaca(), resultado.getPlaca());
        }

        @Test
        @DisplayName("Quando alteramos apenas a placa do entregador com dados inválidos (em branco)")
        void quandoAlteramosCorDoEntregadorInvalidoBanco() throws Exception {
            // Arrange
            EntregadorPlacaPatchRequestDTO entregadorPlacaPatchRequestDTO = EntregadorPlacaPatchRequestDTO.builder()
                    .placa("")
                    .build();

            // Act
            String responseJsonString = driver.perform(patch("/v1/entregadores/" + entregador.getId() + "/placa")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPlacaPatchRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("Placa do carro obrigatorio", resultado.getErrors().get(0));
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de regras sobre o veiculo")
    class EntregadorVerificacaoVeiculo {
        @Test
        @DisplayName("Quando alteramos o veiculo do entregador com dados válidos")
        void quandoAlteramosVeiculoDoEntregadorValido() throws Exception {
            // Arrange
            EntregadorVeiculoPatchRequestDTO entregadorVeiculoPatchRequestDTO = EntregadorVeiculoPatchRequestDTO.builder()
                    .veiculo("moto")
                    .build();

            // Act
            String responseJsonString = driver.perform(patch("/v1/entregadores/" + entregador.getId() + "/veiculo")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorVeiculoPatchRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Entregador resultado = objectMapper.readValue(responseJsonString, Entregador.EntregadorBuilder.class).build();

            // Assert
            assertEquals(entregadorVeiculoPatchRequestDTO.getVeiculo(), resultado.getVeiculo());
        }

        @Test
        @DisplayName("Quando alteramos apenas o veiculo do entregador com dados inválidos (em branco)")
        void quandoAlteramosVeiculoDoEntregadorInvalidoBanco() throws Exception {
            // Arrange
            EntregadorVeiculoPatchRequestDTO entregadorVeiculoPatchRequestDTO = EntregadorVeiculoPatchRequestDTO.builder()
                    .veiculo("")
                    .build();

            // Act
            String responseJsonString = driver.perform(patch("/v1/entregadores/" + entregador.getId() + "/veiculo")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorVeiculoPatchRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("Veiculo obrigatorio", resultado.getErrors().get(0));
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de campos obrigatórios")
    class EntregadorVerificacaoFluxosBasicosApiRest  {

        final String URI_ENTREGADORES = "/v1/entregadores";
        EntregadorPostPutRequestDTO entregadorPutRequestDTO;
        EntregadorPostPutRequestDTO entregadorPostRequestDTO;

        @BeforeEach
        void setup() {
            entregadorPostRequestDTO = EntregadorPostPutRequestDTO.builder()
                    .cor("prata")
                    .nome("Lucas")
                    .veiculo("Fuskinha")
                    .placa("20103")
                    .entregando(false)
                    .build();
            entregadorPutRequestDTO = EntregadorPostPutRequestDTO.builder()
                    .nome("Jao")
                    .veiculo("moto")
                    .cor("vermelho")
                    .placa("72G34")
                    .entregando(false)
                    .build();
        }

        @Test
        @DisplayName("Quando buscamos por todos entregadores salvos")
        void quandoBuscamosPorTodosEntregadoresSalvos() throws Exception {
            // Arrange
            // Vamos ter 3 produtos no banco
            Entregador entregador1 = Entregador.builder()
                    .nome("Lucas")
                    .veiculo("carro")
                    .cor("preto")
                    .placa("76G34")
                    .build();
            Entregador entregador2 = Entregador.builder()
                    .nome("Lucas")
                    .veiculo("carro")
                    .cor("preto")
                    .placa("76G34")
                    .build();
            entregadorRepository.saveAll(Arrays.asList(entregador1, entregador2));

            // Act
            String responseJsonString = driver.perform(get(URI_ENTREGADORES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPostRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<Entregador> resultado = objectMapper.readValue(responseJsonString, new TypeReference<List<Entregador>>(){});


            // Assert
            assertAll(
                    () -> assertEquals(3, resultado.size())
            );
        }

        @Test
        @DisplayName("Quando buscamos um entregador salvo pelo id")
        void quandoBuscamosPorUmEntregadorSalvo() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(get(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPostRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<Entregador> listaResultados = objectMapper.readValue(responseJsonString, new TypeReference<List<Entregador>>(){});
            Entregador resultado = listaResultados.stream().findFirst().orElse(Entregador.builder().build());

            // Assert
            assertAll(
                    () -> assertEquals(entregador.getId().longValue(), resultado.getId().longValue()),
                    () -> assertEquals(entregador.getNome(), resultado.getNome()),
                    () -> assertEquals(entregador.getCor(), resultado.getCor()),
                    () -> assertEquals(entregador.getPlaca(), resultado.getPlaca()),
                    () -> assertEquals(entregador.getVeiculo(), resultado.getVeiculo())
            );

        }

        @Test
        @DisplayName("Quando criamos um novo entregador com dados válidos")
        void quandoCriarEntregadorValido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(post(URI_ENTREGADORES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPostRequestDTO)))
                    .andExpect(status().isCreated()) // Codigo 201
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Entregador resultado = objectMapper.readValue(responseJsonString, Entregador.EntregadorBuilder.class).build();

            // Assert
            assertAll(
                    () -> assertNotNull(resultado.getId()),
                    () -> assertEquals(entregadorPostRequestDTO.getNome(), resultado.getNome()),
                    () -> assertEquals(entregadorPostRequestDTO.getCor(), resultado.getCor()),
                    () -> assertEquals(entregadorPostRequestDTO.getPlaca(), resultado.getPlaca()),
                    () -> assertEquals(entregadorPostRequestDTO.getVeiculo(), resultado.getVeiculo())
            );

        }

        @Test
        @Transactional
        @DisplayName("Quando alteramos o entregador com dados válidos")
        void quandoAlteramosEntregadorValido() throws Exception {
            // Arrange
            Long entregadorId = entregador.getId();

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPutRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Entregador resultado = objectMapper.readValue(responseJsonString, Entregador.EntregadorBuilder.class).build();

            // Assert
            assertAll(
                    () -> assertEquals(entregadorPutRequestDTO.getNome(), resultado.getNome()),
                    () -> assertEquals(entregadorPutRequestDTO.getCor(), resultado.getCor()),
                    () -> assertEquals(entregadorPutRequestDTO.getPlaca(), resultado.getPlaca()),
                    () -> assertEquals(entregadorPutRequestDTO.getVeiculo(), resultado.getVeiculo())
            );

        }
        @Test
        @DisplayName("Quando excluímos um entregador salvo")
        void quandoExcluimosEntregadorValido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(delete(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent()) // Codigo 204
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertTrue(responseJsonString.isBlank());

        }

    }
}
