package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Funcionario;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .pizzas(new HashSet<>())
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
    class TesteListarCardapio{
        Produto pizza;
        Estabelecimento estabelecimento;
        Produto pizza2;
        @BeforeEach
        void setup(){


            pizza = Produto.builder()
                    .nome("calabresa")
                    .tipo("salgado")
                    .tamanho("média")
                    .preco(10.00)
                    .build();
            pizza2 = Produto.builder()
                    .nome("nutella")
                    .tipo("doce")
                    .tamanho("grande")
                    .preco(36.00)
                    .build();



            HashSet<Produto> pizza3 = new HashSet<>();
            pizza3.add(pizza);
            pizza3.add(pizza2);
            estabelecimento = estabelecimentoRepository.save(
                    Estabelecimento.builder()
                            .nome("Pizzaria")
                            .codigoAcesso(123456)
                            .pizzas(pizza3)
                            .build()
            );



        }


         @Test
         @DisplayName("Lista pizza adicionada em cardapio estabelecimento")
         void listaPizzas() throws Exception{
         //Arrange

         String responseJSONString = driver.perform(get(URI_PIZZAS + "/" + estabelecimento.getId() + "/todasPizzas")
                         .contentType(MediaType.APPLICATION_JSON)
         )
                 .andDo(print())
                 .andExpect(status().isOk())
                 .andReturn().getResponse().getContentAsString();

             Set<Produto> pizzas = objectMapper.readValue(responseJSONString, new TypeReference<Set<Produto>>() {});
             Produto pizza  = pizzas.stream().findFirst().orElse(Produto.builder().build());

             assertEquals(2, pizzas.size());
         }

        @Test
        @DisplayName("Lista pizza adicionada em cardapio estabelecimento")
        void listaPizzasDocesDeUmEstabelecimento() throws Exception{
            //Arrange

            String responseJSONString = driver.perform(get(URI_PIZZAS + "/" + estabelecimento.getId() + "/todasPizzasDoces")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            Set<Produto> pizzasDoces = objectMapper.readValue(responseJSONString, new TypeReference<Set<Produto>>() {});
            Produto pizza  = pizzasDoces.stream().findFirst().orElse(Produto.builder().build());

            assertEquals(1, pizzasDoces.size());
        }

        @Test
        @DisplayName("Lista pizza adicionada em cardapio estabelecimento")
        void listaPizzasSalgadasDeUmEstabelecimento() throws Exception{
            //Arrange

            String responseJSONString = driver.perform(get(URI_PIZZAS + "/" + estabelecimento.getId() + "/todasPizzasSalgadas")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            Set<Produto> pizzasSalgadas = objectMapper.readValue(responseJSONString, new TypeReference<Set<Produto>>() {});
            Produto pizza  = pizzasSalgadas.stream().findFirst().orElse(Produto.builder().build());

            assertEquals(1, pizzasSalgadas.size());
        }


    }

}
