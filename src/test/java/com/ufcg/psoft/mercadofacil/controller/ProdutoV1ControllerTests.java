package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.mercadofacil.exception.CustomErrorType;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;
import com.ufcg.psoft.mercadofacil.dto.produto.*;
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
@DisplayName("Classe de testes do controlador de produtos")
public class ProdutoV1ControllerTests {
    @Autowired
    MockMvc driver;
    @Autowired
    ProdutoRepository produtoRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    Produto produto;
    @BeforeEach
    void setup(){
        objectMapper.registerModule(new JavaTimeModule());
        produto = produtoRepository.save(
                Produto.builder()
                        .nome("portuguesa")
                        .preco(38.00)
                        .tamanho("media")
                        .tipo("salgada")
                        .build()
        );
    }

    @AfterEach
    void tearDown(){produtoRepository.deleteAll();}

    @Nested
    @DisplayName("Conjunto de casos de verificação de regras do nome")
    class ProdutoVerificacaoNome{
        @Test
        @DisplayName("Quando alteramos o nome do produto com dados válidos")
        void quandoAlteramosNomedoProdutoValido() throws Exception{
            //Arrange
            ProdutoNomePatchRequestDTO produtoNomePatchRequestDTO = ProdutoNomePatchRequestDTO.builder()
                    .nome("Calabresa")
                    .build();
            //Act
            String responseJsonString = driver.perform(patch("/v1/produtos/" + produto.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produtoNomePatchRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            //Assert
            assertEquals(produtoNomePatchRequestDTO.getNome(), resultado.getNome());
        }

        @Test
        @DisplayName("Quando alteramos o nome do produto com dados inválidos (nome em branco")
        void quandoAlteramosNomedoProdutoInvalido() throws Exception{
            //Arrange
            ProdutoNomePatchRequestDTO produtoNomePatchRequestDTO = ProdutoNomePatchRequestDTO.builder()
                    .nome("")
                    .build();
            //Act
            String responseJsonString = driver.perform(patch("/v1/produtos/" + produto.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produtoNomePatchRequestDTO)))
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
        void quandoAlteramosPrecodoProdutoValido() throws Exception{
            //Arrange
            ProdutoPrecoPatchRequestDTO produtoPrecoPatchRequestDTO = ProdutoPrecoPatchRequestDTO.builder()
                    .preco(34.00)
                    .build();
            //Act
            String responseJsonString = driver.perform(patch("/v1/produtos/" + produto.getId() + "/preco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produtoPrecoPatchRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            //Assert
            assertEquals(produtoPrecoPatchRequestDTO.getPreco(), resultado.getPreco());
        }

        @Test
        @DisplayName("Quando alteramos o preço do produto com dados inválidos (número negativo)")
        void quandoAlteramosPrecodoProdutoInvalido() throws Exception{
            //Arrange
            ProdutoPrecoPatchRequestDTO produtoPrecoPatchRequestDTO = ProdutoPrecoPatchRequestDTO.builder()
                    .preco(-20.00)
                    .build();
            //Act
            String responseJsonString = driver.perform(patch("/v1/produtos/" + produto.getId() + "/preco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produtoPrecoPatchRequestDTO)))
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
    @DisplayName("Conjunto de casos de verificação de regras do tamanho")
    class ProdutoVerificacaoTamanho{
        @Test
        @DisplayName("Quando alteramos o tamanho do produto com dados válidos")
        void quandoAlteramosTamanhodoProdutoValido() throws Exception{
            //Arrange
            ProdutoTamanhoPatchRequestDTO produtoTamanhoPatchRequestDTO = ProdutoTamanhoPatchRequestDTO.builder()
                    .tamanho("GRANDE")
                    .build();
            //Act
            String responseJsonString = driver.perform(patch("/v1/produtos/" + produto.getId() + "/tamanho")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produtoTamanhoPatchRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            //Assert
            assertEquals(produtoTamanhoPatchRequestDTO.getTamanho(), resultado.getTamanho());
        }

        @Test
        @DisplayName("Quando alteramos o tamanho do produto com dados inválidos (tamanho em branco)")
        void quandoAlteramosTamanhodoProdutoInvalido() throws Exception{
            //Arrange
            ProdutoTamanhoPatchRequestDTO produtoTamanhoPatchRequestDTO = ProdutoTamanhoPatchRequestDTO.builder()
                    .tamanho("")
                    .build();
            //Act
            String responseJsonString = driver.perform(patch("/v1/produtos/" + produto.getId() + "/tamanho")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produtoTamanhoPatchRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            //Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertTrue(resultado.getErrors().contains("O tamanho deve ser MEDIO ou GRANDE"));
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de regras do tipo")
    class ProdutoVerificacaoTipo{
        @Test
        @DisplayName("Quando alteramos o tipo do produto com dados válidos")
        void quandoAlteramosTipodoProdutoValido() throws Exception{
            //Arrange
            ProdutoTipoPatchRequestDTO produtoTipoPatchRequestDTO = ProdutoTipoPatchRequestDTO.builder()
                    .tipo("doce")
                    .build();
            //Act
            String responseJsonString = driver.perform(patch("/v1/produtos/" + produto.getId() + "/tipo")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produtoTipoPatchRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            //Assert
            assertEquals(produtoTipoPatchRequestDTO.getTipo(), resultado.getTipo());
        }

        @Test
        @DisplayName("Quando alteramos o tipo do produto com dados inválidos (tamanho em branco)")
        void quandoAlteramosTipodoProdutoInvalido() throws Exception{
            //Arrange
            ProdutoTipoPatchRequestDTO produtoTipoPatchRequestDTO = ProdutoTipoPatchRequestDTO.builder()
                    .tipo("")
                    .build();
            //Act
            String responseJsonString = driver.perform(patch("/v1/produtos/" + produto.getId() + "/tipo")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produtoTipoPatchRequestDTO)))
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

        final String URI_PRODUTOS = "/v1/produtos";

        ProdutoPostPutRequestDTO produtoPutRequestDTO;
        ProdutoPostPutRequestDTO produtoPostRequestDTO;

        @BeforeEach
        void setup(){
            produtoPostRequestDTO = ProdutoPostPutRequestDTO.builder()
                    .nome("portuguesa")
                    .preco(38.00)
                    .tamanho("media")
                    .tipo("salgada")
                    .build();
            produtoPutRequestDTO = ProdutoPostPutRequestDTO.builder()
                    .nome("chocolate")
                    .preco(40.00)
                    .tamanho("grande")
                    .tipo("doce")
                    .build();
        }

        @Test
        @DisplayName("Quando buscamos todos os produtos salvos")
        void quandoBuscamosTodosOsProdutosSalvos() throws Exception{
            //Arrange
            Produto produto1 = Produto.builder()
                    .nome("marguerita")
                    .preco(30.00)
                    .tamanho("media")
                    .tipo("salgada")
                    .build();
            Produto produto2 = Produto.builder()
                    .nome("quatro queijos")
                    .preco(25.00)
                    .tamanho("media")
                    .tipo("salgada")
                    .build();
            Produto produto3 = Produto.builder()
                    .nome("frango com catupiry")
                    .preco(40.00)
                    .tamanho("grande")
                    .tipo("salgada")
                    .build();
            produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));

            //Act
            String responseJsonString = driver.perform(get(URI_PRODUTOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produtoPostRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<Produto> resultado = objectMapper.readValue(responseJsonString, new TypeReference<List<Produto>>() {});

            assertAll(
                    () -> assertEquals(4, resultado.size())
            );
        }

        @Test
        @DisplayName("Quando buscamos um produto salvo pelo id")
        void quandoBuscamosPorUmProdutoSalvo() throws Exception {
            // Arrange
            // Apenas o setup é necessário

            // Act
            String responseJsonString = driver.perform(get(URI_PRODUTOS + "/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produtoPostRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<Produto> listaResultados = objectMapper.readValue(responseJsonString, new TypeReference<List<Produto>>(){});
            Produto resultado = listaResultados.stream().findFirst().orElse(Produto.builder().build());

            // Assert
            assertAll(
                    () -> assertEquals(produto.getId().longValue(), resultado.getId().longValue()),
                    () -> assertEquals(produto.getNome(), resultado.getNome()),
                    () -> assertEquals(produto.getPreco(), resultado.getPreco()),
                    () -> assertEquals(produto.getTamanho(), resultado.getTamanho()),
                    () -> assertEquals(produto.getTipo(), resultado.getTipo())
            );
        }

        @Test
        @DisplayName("Quando criamos um novo produto com dados válidos")
        void quandoCriamosProdutoValido() throws Exception {
            // Arrange
            // Apenas o setup é necessário

            // Act
            String responseJsonString = driver.perform(post(URI_PRODUTOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produtoPostRequestDTO)))
                    .andExpect(status().isCreated()) // Codigo 201
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            // Assert
            assertAll(
                    () -> assertNotNull(resultado.getId()),
                    () -> assertEquals(produtoPostRequestDTO.getNome(), resultado.getNome()),
                    () -> assertEquals(produtoPostRequestDTO.getPreco(), resultado.getPreco()),
                    () -> assertEquals(produtoPostRequestDTO.getTamanho(), resultado.getTamanho()),
                    () -> assertEquals(produtoPostRequestDTO.getTipo(), resultado.getTipo())
            );

        }

        @Test
        @Transactional
        @DisplayName("Quando alteramos um produto com dados válidos")
        void quandoAlteramosProdutoValido() throws Exception {
            // Arrange
            Long produtoId = produto.getId();

            // Act
            String responseJsonString = driver.perform(put(URI_PRODUTOS + "/" + produtoId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(produtoPutRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

            // Assert
            assertAll(
                    () -> assertEquals(produtoPutRequestDTO.getNome(), resultado.getNome()),
                    () -> assertEquals(produtoPutRequestDTO.getPreco(), resultado.getPreco()),
                    () -> assertEquals(produtoPutRequestDTO.getTamanho(), resultado.getTamanho()),
                    () -> assertEquals(produtoPutRequestDTO.getTipo(), resultado.getTipo())
            );

        }

        @Test
        @DisplayName("Quando excluímos um produto salvo")
        void quandoExcluimosProdutoValido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(delete(URI_PRODUTOS + "/" + produto.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent()) // Codigo 204
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertTrue(responseJsonString.isBlank());
        }
    }

}