package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.util.HashSet;

import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes de cardapio do estabelecimento")
public class EstabelecimentoV1ListarCardapioTests {
    @Autowired
    MockMvc driver;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    Estabelecimento estabelecimento;
    Estabelecimento estabelecimento2;
    ObjectMapper objectMapper = new ObjectMapper();
    EstabelecimentoPostPutRequestDTO estabelecimentoPutRequestDTO;
    EstabelecimentoPostPutRequestDTO estabelecimentoPostRequestDTO;
    final String URI_PRODUTOS = "/v1/estabelecimentos";

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
        Pizza pizza;
        Estabelecimento estabelecimento;
        Pizza pizza2;
        @BeforeEach
        void setup(){
            pizza = Pizza.builder()
                    .nome("calabresa")
                    .tipo("salgado")
                    .tamanho("média")
                    .valor(10.00)
                    .build();
            pizza2 = Pizza.builder()
                    .nome("nutella")
                    .tipo("doce")
                    .tamanho("grande")
                    .valor(36.00)
                    .build();
            estabelecimento.getPizzas().add(pizza);
            estabelecimentoRepository.save(estabelecimento);
            estabelecimento.getPizzas().add(pizza2);


        }


         @Test
         @DisplayName("Lista pizza adicionada em cardapio estabelecimento")
         void listaPizzas() throws Exception{
         //Arrange


         //Act
         String responseJsonString = driver.perform(put(URI_PRODUTOS + "/" + estabelecimento.getId() + "/solicitar?idEstabelecimento=" + estabelecimento.getId())
         .contentType(MediaType.APPLICATION_JSON)
         .content(objectMapper.writeValueAsString(pizza)))
         .andExpect(status().isOk()) // Codigo 200
         .andDo(print())
         .andReturn().getResponse().getContentAsString();

         Estabelecimento resultado = objectMapper.readValue(responseJsonString, Estabelecimento.EstabelecimentoBuilder.class).build();

         }

    }

}
