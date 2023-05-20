package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Funcionario;
import com.ufcg.psoft.mercadofacil.model.Pizza;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Testes de cardapio do estabelecimento")
public class CardapioV1ControllerTests {
    @Autowired
    MockMvc driver;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    Estabelecimento estabelecimento;
    Estabelecimento estabelecimento2;

    ObjectMapper objectMapper = new ObjectMapper();
    EstabelecimentoPostPutRequestDTO estabelecimentoPutRequestDTO;
    EstabelecimentoPostPutRequestDTO estabelecimentoPostRequestDTO;

    final String URI_PIZZAS = "/v1/listarPizzas";

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        estabelecimento = estabelecimentoRepository.save(Estabelecimento.builder()
                .nome("Sorveteria")
                .espera(new HashSet<Funcionario>())
                .entregadores(new HashSet<>())
                .cardapio(new HashSet<>())
                .build()
        );
        estabelecimento2 = Estabelecimento.builder()
                .nome("Pizzando")
                .id(123489L)
                .build();
        estabelecimentoPostRequestDTO = EstabelecimentoPostPutRequestDTO.builder()
                .nome("Estabelecimento Dez")
                .build();
        estabelecimentoPutRequestDTO = EstabelecimentoPostPutRequestDTO.builder()
                .nome("Estabelecimento Vinte")
                .build();
    }

    @AfterEach
    void tearDown() {
        estabelecimentoRepository.deleteAll();
    }

    @Nested
    @DisplayName("Teste de listagem cardapio")
    class TesteListarCardapio {
        Produto pizza;
        Estabelecimento estabelecimento;
        Produto pizza2;

        @BeforeEach
        void setup() {


            pizza = Produto.builder()
                    .nome("calabresa")
                    .tipo("salgado")
                    .tamanho("media")
                    .preco(10.00)
                    .build();
            pizza2 = Produto.builder()
                    .nome("nutella")
                    .tipo("doce")
                    .tamanho("grande")
                    .preco(36.00)
                    .build();


            HashSet<Pizza> pizza3 = new HashSet<>();
            pizza3.add(pizza);
            pizza3.add(pizza2);
            estabelecimento = estabelecimentoRepository.save(
                    Estabelecimento.builder()
                            .nome("Pizzaria")
                            .codigoAcesso(123456)
                            .cardapio(pizza3)
                            .build()
            );


        }


        @Test
        @DisplayName("Lista pizzas adicionadas em cardapio estabelecimento")
        void listaPizzas() throws Exception {
            //Arrange

            String responseJSONString = driver.perform(get(URI_PIZZAS + "/" + estabelecimento.getId() + "/todasPizzas")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            Set<Produto> pizzas = objectMapper.readValue(responseJSONString, new TypeReference<Set<Produto>>() {
            });
            Produto pizza = pizzas.stream().findFirst().orElse(Produto.builder().build());

            assertEquals(2, pizzas.size());
        }

        @Test
        @DisplayName("Lista pizza do tipo doce e tamanho grande adicionada em cardapio estabelecimento, listagem apenas para pizzas doces")
        void listaPizzasDocesDeUmEstabelecimentoTamanhoGrande() throws Exception {
            //Arrange

            String responseJSONString = driver.perform(get(URI_PIZZAS + "/" + estabelecimento.getId() + "/todasPizzasDoces")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            Set<Produto> pizzasDoces = objectMapper.readValue(responseJSONString, new TypeReference<Set<Produto>>() {
            });
            Produto pizzaRetorno = pizzasDoces.stream().findFirst().orElse(Produto.builder().build());

            assertEquals(1, pizzasDoces.size());
            assertAll(
                    () -> assertEquals(pizza2.getNome(), pizzaRetorno.getNome()),
                    () -> assertEquals(pizza2.getTamanho(), pizzaRetorno.getTamanho()),
                    () -> assertEquals(pizza2.getPreco(), pizzaRetorno.getPreco()),
                    () -> assertEquals(pizza2.getTipo(), pizzaRetorno.getTipo())
            );

        }


        @Test
        @DisplayName("Lista pizza adicionada em cardapio estabelecimento para pizza salgada e de tamanho medio")
        void listaPizzasSalgadasDeUmEstabelecimento() throws Exception {
            //Arrange

            String responseJSONString = driver.perform(get(URI_PIZZAS + "/" + estabelecimento.getId() + "/todasPizzasSalgadas")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            Set<Produto> pizzasSalgadas = objectMapper.readValue(responseJSONString, new TypeReference<Set<Produto>>() {
            });
            Produto pizzaRetorno = pizzasSalgadas.stream().findFirst().orElse(Produto.builder().build());

            assertEquals(1, pizzasSalgadas.size());

            assertAll(
                    () -> assertEquals(pizza.getNome(), pizzaRetorno.getNome()),
                    () -> assertEquals(pizza.getTamanho(), pizzaRetorno.getTamanho()),
                    () -> assertEquals(pizza.getPreco(), pizzaRetorno.getPreco()),
                    () -> assertEquals(pizza.getTipo(), pizzaRetorno.getTipo())
            );
        }

        @Test
        @DisplayName("Lista pizza do tipo salgada e tamanho grande adicionada em cardapio estabelecimento")
        void verificaListagemPizzaSalgadaGrande() throws Exception {
            //Arrange
            Produto pizzaSalgadaGrande = Produto.builder()
                    .nome("calabresa")
                    .tipo("salgado")
                    .tamanho("grande")
                    .preco(30.00)
                    .build();

            estabelecimento.getPizzas().add(pizzaSalgadaGrande);

            //Act
            String responseJSONString = driver.perform(get(URI_PIZZAS + "/" + estabelecimento.getId() + "/todasPizzasSalgadas")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            Set<Produto> pizzasSalgadas = objectMapper.readValue(responseJSONString, new TypeReference<Set<Produto>>() {
            });
            Produto pizzaRetorno = pizzasSalgadas.stream().filter(produto -> ("salgado".equals(produto.getTipo()) && "grande".equals(produto.getTamanho()))).findFirst().orElse(Produto.builder().build());

            assertEquals(2, pizzasSalgadas.size());

            assertAll(
                    () -> assertEquals(pizzaSalgadaGrande.getNome(), pizzaRetorno.getNome()),
                    () -> assertEquals(pizzaSalgadaGrande.getTamanho(), pizzaRetorno.getTamanho()),
                    () -> assertEquals(pizzaSalgadaGrande.getPreco(), pizzaRetorno.getPreco()),
                    () -> assertEquals(pizzaSalgadaGrande.getTipo(), pizzaRetorno.getTipo())
            );

        }

        @Test
        @DisplayName("Lista pizza do tipo doce e tamanho medio adicionada em cardapio estabelecimento")
        void verificaListagemPizzasDocesTamanhoMedia() throws Exception {
            //Arrange
            Produto pizzaDoceMedia = Produto.builder()
                    .nome("nutella")
                    .tipo("doce")
                    .tamanho("media")
                    .preco(15.00)
                    .build();

            estabelecimento.getPizzas().add(pizzaDoceMedia);

            //Act
            String responseJSONString = driver.perform(get(URI_PIZZAS + "/" + estabelecimento.getId() + "/todasPizzasDoces")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            Set<Produto> pizzasDoces = objectMapper.readValue(responseJSONString, new TypeReference<Set<Produto>>() {
            });
            Produto pizzaRetorno = pizzasDoces.stream()
                    .filter(produto -> ("doce".equals(produto.getTipo()) && "media".equals(produto.getTamanho())))
                    .findFirst().orElse(Produto.builder().build());

            assertEquals(2, pizzasDoces.size());

            assertAll(
                    () -> assertEquals(pizzaDoceMedia.getNome(), pizzaRetorno.getNome()),
                    () -> assertEquals(pizzaDoceMedia.getTamanho(), pizzaRetorno.getTamanho()),
                    () -> assertEquals(pizzaDoceMedia.getPreco(), pizzaRetorno.getPreco()),
                    () -> assertEquals(pizzaDoceMedia.getTipo(), pizzaRetorno.getTipo())
            );

        }


        @Test
        @DisplayName("verificacao de existencia correta do estabelecimento no sistema")
        void verificaCodigoDeAcessoEstabelecimentoEOutros() throws Exception {
            //Arrange

            String responseJSONString = driver.perform(get("/v1/estabelecimentos" + "/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            Estabelecimento estabelecimentoResultante = objectMapper.readValue(responseJSONString, Estabelecimento.EstabelecimentoBuilder.class).build();

            // Assert

            assertAll(
                    () -> assertEquals(estabelecimento.getNome(), estabelecimentoResultante.getNome()),
                    () -> assertEquals(estabelecimento.getCodigoAcesso(), estabelecimentoResultante.getCodigoAcesso()),
                    () -> assertEquals(estabelecimento.getPizzas().size(), estabelecimentoResultante.getPizzas().size())
            );
        }

    }
}
**/