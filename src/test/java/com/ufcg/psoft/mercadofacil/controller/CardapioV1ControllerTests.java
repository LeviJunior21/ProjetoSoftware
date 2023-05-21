package com.ufcg.psoft.mercadofacil.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        Sabor pizza;
        Estabelecimento estabelecimento;
        Sabor pizza2;

        @BeforeEach
        void setup() {


            pizza = Sabor.builder()
                    .nome("calabresa")
                    .tipo("salgado")
                    .tamanho("media")
                    .preco(10.00)
                    .build();
            pizza2 = Sabor.builder()
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

            Set<Sabor> pizzas = objectMapper.readValue(responseJSONString, new TypeReference<Set<Sabor>>() {
            });
            Sabor pizza = pizzas.stream().findFirst().orElse(Sabor.builder().build());

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

            Set<Sabor> pizzasDoces = objectMapper.readValue(responseJSONString, new TypeReference<Set<Sabor>>() {
            });
            Sabor pizzaRetorno = pizzasDoces.stream().findFirst().orElse(Sabor.builder().build());

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

            Set<Sabor> pizzasSalgadas = objectMapper.readValue(responseJSONString, new TypeReference<Set<Sabor>>() {
            });
            Sabor pizzaRetorno = pizzasSalgadas.stream().findFirst().orElse(Sabor.builder().build());

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
            Sabor pizzaSalgadaGrande = Sabor.builder()
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

            Set<Sabor> pizzasSalgadas = objectMapper.readValue(responseJSONString, new TypeReference<Set<Sabor>>() {
            });
            Sabor pizzaRetorno = pizzasSalgadas.stream().filter(sabor -> ("salgado".equals(sabor.getTipo()) && "grande".equals(sabor.getTamanho()))).findFirst().orElse(Sabor.builder().build());

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
            Sabor pizzaDoceMedia = Sabor.builder()
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

            Set<Sabor> pizzasDoces = objectMapper.readValue(responseJSONString, new TypeReference<Set<Sabor>>() {
            });
            Sabor pizzaRetorno = pizzasDoces.stream()
                    .filter(sabor -> ("doce".equals(sabor.getTipo()) && "media".equals(sabor.getTamanho())))
                    .findFirst().orElse(Sabor.builder().build());

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