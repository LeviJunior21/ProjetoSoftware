package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.mercadofacil.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Pizza;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.ufcg.psoft.mercadofacil.exception.CustomErrorType;
import java.util.HashSet;
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

    @Test
    @DisplayName("Quando excluimos o estabelecimento")
    void quandoExcluimosUmEstabelecimento() throws Exception {
        // Arrange
        String responseJsonString = driver.perform(delete(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()) // Codigo 204
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        // Assert
        assertTrue(responseJsonString.isBlank());
    }


    @Nested
    @DisplayName("Testes para aceitações dos pedidos no estabelecimento.")
    class TestePedidosAceitacoes {

        Entregador entregador;
        EntregadorPostPutRequestDTO entregadorPostPutRequestDTO;
        @BeforeEach
        void setup2() {
            entregadorPostPutRequestDTO = EntregadorPostPutRequestDTO.builder()
                    .nome("Lucas")
                    .placa("131231")
                    .cor("vermelho")
                    .veiculo("moto")
                    .build();
        }

        @Test
        @DisplayName("Quando um entregador solicita pedido para um estabelecimento")
        void quandoEntregadorSolicitaPedidoEstabelecimento() throws Exception {
            //Arrange
            //Act
            String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/solicitar?idEstabelecimento=" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Estabelecimento resultado = objectMapper.readValue(responseJsonString, Estabelecimento.EstabelecimentoBuilder.class).build();
            //Assert
            assertEquals(1, resultado.getEntregadores().size());
        }
    }

    public class TesteListarCardapio{
        Pizza pizza;
        Estabelecimento estabelecimento;
        @BeforeEach
        void setup(){
            pizza = Pizza.builder()
                    .nome("calabresa")
                    .tipo("salgado")
                    .tamanho("média")
                    .valor(10.00)
                    .build();

            estabelecimento.getPizzas().add(pizza);
        }

        /**
        @Test
        @DisplayName("Lista pizza adicionada em cardapio estabelecimento")
        void listaPizzas(){
            //Arrange
            Pizza pizza2 = Pizza.builder()
                    .nome("nutella")
                    .tipo("doce")
                    .tamanho("grande")
                    .valor(36.00)
                    .build();
            estabelecimento.getPizzas().add(pizza2);

            //Act
            String responseJsonString = driver.perform(put(URI_PRODUTOS + "/" + estabelecimento.getId() + "/solicitar?idEstabelecimento=" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Estabelecimento resultado = objectMapper.readValue(responseJsonString, Estabelecimento.EstabelecimentoBuilder.class).build();

        }
        */
    }

}
