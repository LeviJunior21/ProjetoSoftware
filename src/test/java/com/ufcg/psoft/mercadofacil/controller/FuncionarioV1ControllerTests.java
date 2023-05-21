package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.mercadofacil.dto.funcionario.*;
import com.ufcg.psoft.mercadofacil.exception.CustomErrorType;
import com.ufcg.psoft.mercadofacil.model.Funcionario;
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
    FuncionarioRepository funcionarioRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    Funcionario funcionario;
    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        funcionario = funcionarioRepository.save(
                Funcionario.builder()
                        .cor("prata")
                        .nome("Lucas")
                        .veiculo("Fuskinha")
                        .placa("20103")
                        .codigoAcesso(324458)
                        .build()
        );
    }

    @AfterEach
    void tearDown() {
        funcionarioRepository.deleteAll();
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de regras sobre o nome")
    class FuncionarioVerificacaoNome {
        @Test
        @DisplayName("Quando alteramos o nome do funcionário com dados válidos")
        void quandoAlteramosNomeDoFuncionarioValido() throws Exception {
            // Arrange
            FuncionarioNomePatchRequestDTO funcionarioNomePatchRequestDTO = FuncionarioNomePatchRequestDTO.builder()
                    .nome("Joao Silva")
                    .codigoAcesso(324458)
                    .build();

            // Act
            String responseJsonString = driver.perform(patch("/v1/funcionarios/" + funcionario.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(funcionarioNomePatchRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Funcionario resultado = objectMapper.readValue(responseJsonString, Funcionario.FuncionarioBuilder.class).build();

            // Assert
            assertEquals(funcionarioNomePatchRequestDTO.getNome(), resultado.getNome());
        }

        @Test
        @DisplayName("Quando alteramos apenas o nome do funcionário com dados inválidos (em branco)")
        void quandoAlteramosNomeDoFuncionarioInvalidoBanco() throws Exception {
            // Arrange
            FuncionarioNomePatchRequestDTO funcionarioNomePatchRequestDTO = FuncionarioNomePatchRequestDTO.builder()
                    .nome("")
                    .codigoAcesso(324458)
                    .build();

            // Act
            String responseJsonString = driver.perform(patch("/v1/funcionarios/" + funcionario.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(funcionarioNomePatchRequestDTO)))
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
    class FuncionarioVerificacaoCor {
        @Test
        @DisplayName("Quando alteramos a cor do funcionário com dados válidos")
        void quandoAlteramosCorDoFuncionarioValido() throws Exception {
            // Arrange
            FuncionarioCorPatchRequestDTO funcionarioCorPatchRequestDTO = FuncionarioCorPatchRequestDTO.builder()
                    .cor("azul")
                    .codigoAcesso(324458)
                    .build();

            // Act
            String responseJsonString = driver.perform(patch("/v1/funcionarios/" + funcionario.getId() + "/cor")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(funcionarioCorPatchRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            FuncionarioDTO resultado = objectMapper.readValue(responseJsonString, FuncionarioDTO.FuncionarioDTOBuilder.class).build();

            // Assert
            assertEquals(funcionarioCorPatchRequestDTO.getCor(), resultado.getCor());
        }
    }

    @Test
    @DisplayName("Quando alteramos apenas a cor do funcionário com dados inválidos (em branco)")
    void quandoAlteramosCorDoFuncionarioInvalidoBanco() throws Exception {
        // Arrange
        FuncionarioCorPatchRequestDTO funcionarioCorPatchRequestDTO = FuncionarioCorPatchRequestDTO.builder()
                .cor("")
                .codigoAcesso(324458)
                .build();

        // Act
        String responseJsonString = driver.perform(patch("/v1/funcionarios/" + funcionario.getId() + "/cor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionarioCorPatchRequestDTO)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

        // Assert
        assertEquals("Erros de validacao encontrados", resultado.getMessage());
        assertEquals("Cor do carro obrigatorio", resultado.getErrors().get(0));
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de regras sobre a placa")
    class FuncionarioVerificacaoPlaca {
        @Test
        @DisplayName("Quando alteramos a placa do funcionário com dados válidos")
        void quandoAlteramosPlacaDoFuncionarioValido() throws Exception {
            // Arrange
            FuncionarioPlacaPatchRequestDTO funcionarioPlacaPatchRequestDTO = FuncionarioPlacaPatchRequestDTO.builder()
                    .placa("5atfa4")
                    .codigoAcesso(324458)
                    .build();

            // Act
            String responseJsonString = driver.perform(patch("/v1/funcionarios/" + funcionario.getId() + "/placa")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(funcionarioPlacaPatchRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Funcionario resultado = objectMapper.readValue(responseJsonString, Funcionario.FuncionarioBuilder.class).build();

            // Assert
            assertEquals(funcionarioPlacaPatchRequestDTO.getPlaca(), resultado.getPlaca());
        }

        @Test
        @DisplayName("Quando alteramos apenas a placa do funcionário com dados inválidos (em branco)")
        void quandoAlteramosPlacaDoFuncionarioInvalidoBanco() throws Exception {
            // Arrange
            FuncionarioPlacaPatchRequestDTO funcionarioPlacaPatchRequestDTO = FuncionarioPlacaPatchRequestDTO.builder()
                    .placa("")
                    .codigoAcesso(324458)
                    .build();

            // Act
            String responseJsonString = driver.perform(patch("/v1/funcionarios/" + funcionario.getId() + "/placa")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(funcionarioPlacaPatchRequestDTO)))
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
    @DisplayName("Conjunto de casos de verificação de regras sobre o veículo")
    class FuncionarioVerificacaoVeiculo {
        @Test
        @DisplayName("Quando alteramos o veículo do funcionário com dados válidos")
        void quandoAlteramosVeiculoDoFuncionarioValido() throws Exception {
            // Arrange
            FuncionarioVeiculoPatchRequestDTO funcionarioVeiculoPatchRequestDTO = FuncionarioVeiculoPatchRequestDTO.builder()
                    .veiculo("moto")
                    .codigoAcesso(324458)
                    .build();

            // Act
            String responseJsonString = driver.perform(patch("/v1/funcionarios/" + funcionario.getId() + "/veiculo")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(funcionarioVeiculoPatchRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Funcionario resultado = objectMapper.readValue(responseJsonString, Funcionario.FuncionarioBuilder.class).build();

            // Assert
            assertEquals(funcionarioVeiculoPatchRequestDTO.getVeiculo(), resultado.getVeiculo());
        }

        @Test
        @DisplayName("Quando alteramos apenas o veículo do funcionário com dados inválidos (em branco)")
        void quandoAlteramosVeiculoDoFuncionarioInvalidoBanco() throws Exception {
            // Arrange
            FuncionarioVeiculoPatchRequestDTO funcionarioVeiculoPatchRequestDTO = FuncionarioVeiculoPatchRequestDTO.builder()
                    .veiculo("")
                    .codigoAcesso(324458)
                    .build();

            // Act
            String responseJsonString = driver.perform(patch("/v1/funcionarios/" + funcionario.getId() + "/veiculo")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(funcionarioVeiculoPatchRequestDTO)))
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
    class FuncionarioVerificacaoFluxosBasicosApiRest  {

        final String URI_FUNCIONARIOS = "/v1/funcionarios";
        FuncionarioPostPutRequestDTO funcionarioPutRequestDTO;
        FuncionarioPostPutRequestDTO funcionarioPostRequestDTO;
        FuncionarioRemoveRequestDTO funcionarioRemoveRequestDTO;

        @BeforeEach
        void setup() {
            funcionarioPostRequestDTO = FuncionarioPostPutRequestDTO.builder()
                    .cor("prata")
                    .nome("Lucas")
                    .codigoAcesso(324458)
                    .veiculo("Fuskinha")
                    .placa("20103")
                    .entregando(false)
                    .build();
            funcionarioPutRequestDTO = FuncionarioPostPutRequestDTO.builder()
                    .nome("Jao")
                    .codigoAcesso(324458)
                    .veiculo("moto")
                    .cor("vermelho")
                    .placa("72G34")
                    .entregando(false)
                    .build();
        }

        @Test
        @DisplayName("Quando buscamos por todos funcionários salvos")
        void quandoBuscamosPorTodosFuncionariosSalvos() throws Exception {
            // Arrange
            // Vamos ter 3 funcionários no banco
            Funcionario funcionario1 = Funcionario.builder()
                    .nome("Lucas Silva")
                    .veiculo("carro")
                    .cor("preto")
                    .placa("76G34")
                    .build();
            Funcionario funcionario2 = Funcionario.builder()
                    .nome("Lucas Pereira")
                    .veiculo("moto")
                    .cor("vermelho")
                    .placa("78K56")
                    .build();
            funcionarioRepository.saveAll(Arrays.asList(funcionario1, funcionario2));

            // Act
            String responseJsonString = driver.perform(get(URI_FUNCIONARIOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(funcionarioPostRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<Funcionario> resultado = objectMapper.readValue(responseJsonString, new TypeReference<List<Funcionario>>(){});


            // Assert
            assertAll(
                    () -> assertEquals(4, resultado.size())
            );
        }

        @Test
        @DisplayName("Quando buscamos um funcionário salvo pelo id")
        void quandoBuscamosPorUmFuncionarioSalvo() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(get(URI_FUNCIONARIOS + "/" + funcionario.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(funcionarioPostRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<Funcionario> listaResultados = objectMapper.readValue(responseJsonString, new TypeReference<List<Funcionario>>(){});
            Funcionario resultado = listaResultados.stream().findFirst().orElse(Funcionario.builder().build());

            // Assert
            assertAll(
                    () -> assertEquals(funcionario.getId().longValue(), resultado.getId().longValue()),
                    () -> assertEquals(funcionario.getNome(), resultado.getNome()),
                    () -> assertEquals(funcionario.getCor(), resultado.getCor()),
                    () -> assertEquals(funcionario.getPlaca(), resultado.getPlaca()),
                    () -> assertEquals(funcionario.getVeiculo(), resultado.getVeiculo())
            );

        }

        @Test
        @DisplayName("Quando criamos um novo funcionário com dados válidos")
        void quandoCriarFuncionarioValido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(post(URI_FUNCIONARIOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(funcionarioPostRequestDTO)))
                    .andExpect(status().isCreated()) // Codigo 201
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Funcionario resultado = objectMapper.readValue(responseJsonString, Funcionario.FuncionarioBuilder.class).build();

            // Assert
            assertAll(
                    () -> assertNotNull(resultado.getId()),
                    () -> assertEquals(funcionarioPostRequestDTO.getNome(), resultado.getNome()),
                    () -> assertEquals(funcionarioPostRequestDTO.getCor(), resultado.getCor()),
                    () -> assertEquals(funcionarioPostRequestDTO.getPlaca(), resultado.getPlaca()),
                    () -> assertEquals(funcionarioPostRequestDTO.getVeiculo(), resultado.getVeiculo())
            );

        }

        @Test
        @Transactional
        @DisplayName("Quando alteramos o funcionário com dados válidos")
        void quandoAlteramosFuncionarioValido() throws Exception {
            // Arrange
            Long funcionarioId = funcionario.getId();

            // Act
            String responseJsonString = driver.perform(put(URI_FUNCIONARIOS + "/" + funcionario.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(funcionarioPutRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Funcionario resultado = objectMapper.readValue(responseJsonString, Funcionario.FuncionarioBuilder.class).build();

            // Assert
            assertAll(
                    () -> assertEquals(funcionarioPutRequestDTO.getNome(), resultado.getNome()),
                    () -> assertEquals(funcionarioPutRequestDTO.getCor(), resultado.getCor()),
                    () -> assertEquals(funcionarioPutRequestDTO.getPlaca(), resultado.getPlaca()),
                    () -> assertEquals(funcionarioPutRequestDTO.getVeiculo(), resultado.getVeiculo())
            );

        }

        @Test
        @DisplayName("Quando excluímos um funcionário salvo")
        void quandoExcluimosFuncionarioValido() throws Exception {
            // Arrange
            funcionarioRemoveRequestDTO = FuncionarioRemoveRequestDTO.builder()
                    .codigoAcesso(funcionario.getCodigoAcesso())
                    .build();

            // Act
            String responseJsonString = driver.perform(delete(URI_FUNCIONARIOS + "/" + funcionario.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(funcionarioRemoveRequestDTO))
                    )
                    .andExpect(status().isNoContent()) // Codigo 204
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertTrue(responseJsonString.isBlank());
        }


        @Test
        @DisplayName("Quando tentamos excluir um funcionário salvo com código de acesso inválido")
        void quandoExcluimosFuncionarioComCodigoAcessoInvalido() throws Exception {
            // Arrange
            funcionarioRemoveRequestDTO = FuncionarioRemoveRequestDTO.builder()
                    .codigoAcesso(989898)
                    .build();

            // Act
            String responseJsonString = driver.perform(delete(URI_FUNCIONARIOS + "/" + funcionario.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(funcionarioRemoveRequestDTO))
                    )
                    .andExpect(status().isBadRequest()) // Codigo 204
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O codigo de acesso eh diferente!", resultado.getMessage());
        }
    }
}
