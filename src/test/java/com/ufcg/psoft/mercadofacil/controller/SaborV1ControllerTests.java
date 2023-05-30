package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.mercadofacil.exception.CustomErrorType;
import com.ufcg.psoft.mercadofacil.model.Sabor;
import com.ufcg.psoft.mercadofacil.repository.SaborRepository;
import com.ufcg.psoft.mercadofacil.dto.sabor.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@DisplayName("Classe de testes do controlador de sabores")
public class SaborV1ControllerTests {
    @Autowired
    MockMvc driver;
    @Autowired
    SaborRepository saborRepository;
    final String URI_SABORES = "/v1/sabores";
    ObjectMapper objectMapper = new ObjectMapper();

    Sabor sabor;
    @BeforeEach
    void setup(){
        objectMapper.registerModule(new JavaTimeModule());
        sabor = saborRepository.save(
                Sabor.builder()
                        .nomeSabor("portuguesa")
                        .preco(38.00)
                        .tipo("salgada")
                        .build()
        );
    }

    @AfterEach
    void tearDown(){
        saborRepository.deleteAll();}

    @Nested
    @DisplayName("Conjunto de casos de verificação de regras do nome")
    class ProdutoVerificacaoNome{
        @Test
        @DisplayName("Quando alteramos o nome do sabor com dados válidos")
        void quandoAlteramosNomedoSaborValido() throws Exception{
            //Arrange
            SaborNomePatchRequestDTO saborNomePatchRequestDTO = SaborNomePatchRequestDTO.builder()
                    .nome("Calabresa")
                    .build();
            //Act
            String responseJsonString = driver.perform(patch(URI_SABORES + "/" + sabor.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(saborNomePatchRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Sabor resultado = objectMapper.readValue(responseJsonString, Sabor.SaborBuilder.class).build();

            //Assert
            assertEquals(saborNomePatchRequestDTO.getNome(), resultado.getNomeSabor());
        }

        @Test
        @DisplayName("Quando alteramos o nome do sabor com dados inválidos (nome em branco")
        void quandoAlteramosNomedoSaborInvalido() throws Exception{
            //Arrange
            SaborNomePatchRequestDTO saborNomePatchRequestDTO = SaborNomePatchRequestDTO.builder()
                    .nome("")
                    .build();
            //Act
            String responseJsonString = driver.perform(patch("/v1/sabores/" + sabor.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(saborNomePatchRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            //Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("Nome eh obrigatorio", resultado.getErrors().get(0));
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de regras do preço")
    class ProdutoVerificacaoPreco{
        @Test
        @DisplayName("Quando alteramos o preço do produto com dados válidos")
        void quandoAlteramosPrecodoSaborValido() throws Exception{
            //Arrange
            SaborPrecoPatchRequestDTO saborPrecoPatchRequestDTO = SaborPrecoPatchRequestDTO.builder()
                    .preco(34.00)
                    .build();
            //Act
            String responseJsonString = driver.perform(patch("/v1/sabores/" + sabor.getId() + "/preco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(saborPrecoPatchRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Sabor resultado = objectMapper.readValue(responseJsonString, Sabor.SaborBuilder.class).build();

            //Assert
            assertEquals(saborPrecoPatchRequestDTO.getPreco(), resultado.getPreco());
        }

        @Test
        @DisplayName("Quando alteramos o preço do produto com dados inválidos (número negativo)")
        void quandoAlteramosPrecodoSaborInvalido() throws Exception{
            //Arrange
            SaborPrecoPatchRequestDTO saborPrecoPatchRequestDTO = SaborPrecoPatchRequestDTO.builder()
                    .preco(-20.00)
                    .build();
            //Act
            String responseJsonString = driver.perform(patch("/v1/sabores/" + sabor.getId() + "/preco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(saborPrecoPatchRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            //Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("Preco deve ser maior ou igual a zero", resultado.getErrors().get(0));
        }
    }


    @Nested
    @DisplayName("Conjunto de casos de verificação de regras do tipo")
    class ProdutoVerificacaoTipo{
        @Test
        @DisplayName("Quando alteramos o tipo do produto com dados válidos")
        void quandoAlteramosTipodoSaborValido() throws Exception{
            //Arrange
            SaborTipoPatchRequestDTO saborTipoPatchRequestDTO = SaborTipoPatchRequestDTO.builder()
                    .tipo("doce")
                    .build();
            //Act
            String responseJsonString = driver.perform(patch("/v1/sabores/" + sabor.getId() + "/tipo")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(saborTipoPatchRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Sabor resultado = objectMapper.readValue(responseJsonString, Sabor.SaborBuilder.class).build();

            //Assert
            assertEquals(saborTipoPatchRequestDTO.getTipo(), resultado.getTipo());
        }

        @Test
        @DisplayName("Quando alteramos o tipo do produto com dados inválidos ")
        void quandoAlteramosTipodoSaborInvalido() throws Exception{
            //Arrange
            SaborTipoPatchRequestDTO saborTipoPatchRequestDTO = SaborTipoPatchRequestDTO.builder()
                    .tipo("")
                    .build();
            //Act
            String responseJsonString = driver.perform(patch("/v1/sabores/" + sabor.getId() + "/tipo")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(saborTipoPatchRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            //Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("Tipo da pizza eh obrigatorio", resultado.getErrors().get(0));
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de campos obrigatórios")
    class ProdutoVerificacaoFluxosBasicosApiRest{



        SaborPostPutRequestDTO saborPutRequestDTO;
        SaborPostPutRequestDTO saborPostRequestDTO;

        @BeforeEach
        void setup(){
            saborPostRequestDTO = SaborPostPutRequestDTO.builder()
                    .nome("portuguesa")
                    .preco(38.00)
                    .tipo("salgada")
                    .build();
            saborPutRequestDTO = SaborPostPutRequestDTO.builder()
                    .nome("chocolate")
                    .preco(40.00)
                    .tipo("doce")
                    .build();
        }

        @Test
        @DisplayName("Quando buscamos todos os produtos salvos")
        void quandoBuscamosTodosOsSaboresSalvos() throws Exception{
            //Arrange
            Sabor sabor1 = Sabor.builder()
                    .nomeSabor("marguerita")
                    .preco(30.00)
                    .tipo("salgada")
                    .build();
            Sabor sabor2 = Sabor.builder()
                    .nomeSabor("quatro queijos")
                    .preco(25.00)
                    .tipo("salgada")
                    .build();
            Sabor sabor3 = Sabor.builder()
                    .nomeSabor("frango com catupiry")
                    .preco(40.00)
                    .tipo("salgada")
                    .build();
            saborRepository.saveAll(Arrays.asList(sabor1, sabor2, sabor3));

            //Act
            String responseJsonString = driver.perform(get(URI_SABORES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(saborPostRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<Sabor> resultado = objectMapper.readValue(responseJsonString, new TypeReference<List<Sabor>>() {});

            assertAll(
                    () -> assertEquals(4, resultado.size())
            );
        }

        @Test
        @DisplayName("Quando buscamos um produto salvo pelo id")
        void quandoBuscamosPorUmSaborSalvo() throws Exception {
            // Arrange
            // Apenas o setup é necessário

            // Act
            String responseJsonString = driver.perform(get(URI_SABORES + "/" + sabor.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(saborPostRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<Sabor> listaResultados = objectMapper.readValue(responseJsonString, new TypeReference<List<Sabor>>(){});
            Sabor resultado = listaResultados.stream().findFirst().orElse(Sabor.builder().build());

            // Assert
            assertAll(
                    () -> assertEquals(sabor.getId().longValue(), resultado.getId().longValue()),
                    () -> assertEquals(sabor.getNomeSabor(), resultado.getNomeSabor()),
                    () -> assertEquals(sabor.getPreco(), resultado.getPreco()),
                    () -> assertEquals(sabor.getTipo(), resultado.getTipo())
            );
        }

        @Test
        @DisplayName("Quando criamos um novo produto com dados válidos")
        void quandoCriamosSaborValido() throws Exception {
            // Arrange
            // Apenas o setup é necessário

            // Act
            String responseJsonString = driver.perform(post(URI_SABORES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(saborPostRequestDTO)))
                    .andExpect(status().isCreated()) // Codigo 201
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Sabor resultado = objectMapper.readValue(responseJsonString, Sabor.SaborBuilder.class).build();

            // Assert
            assertAll(
                    () -> assertNotNull(resultado.getId()),
                    () -> assertEquals(saborPostRequestDTO.getNome(), resultado.getNomeSabor()),
                    () -> assertEquals(saborPostRequestDTO.getPreco(), resultado.getPreco()),
                    () -> assertEquals(saborPostRequestDTO.getTipo(), resultado.getTipo())
            );

        }

        @Test
        @Transactional
        @DisplayName("Quando alteramos um produto com dados válidos")
        void quandoAlteramosSaborValido() throws Exception {
            // Arrange
            Long saborId = sabor.getId();

            // Act
            String responseJsonString = driver.perform(put(URI_SABORES + "/" + saborId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(saborPutRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Sabor resultado = objectMapper.readValue(responseJsonString, Sabor.SaborBuilder.class).build();

            // Assert
            assertAll(
                    () -> assertEquals(saborPutRequestDTO.getNome(), resultado.getNomeSabor()),
                    () -> assertEquals(saborPutRequestDTO.getPreco(), resultado.getPreco()),
                    () -> assertEquals(saborPutRequestDTO.getTipo(), resultado.getTipo())
            );

        }

        @Test
        @DisplayName("Quando excluímos um produto salvo")
        void quandoExcluimosSaborValido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(delete(URI_SABORES + "/" + sabor.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent()) // Codigo 204
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertTrue(responseJsonString.isBlank());
        }
    }

}