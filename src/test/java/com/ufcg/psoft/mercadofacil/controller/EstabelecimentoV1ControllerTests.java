package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.*;
import com.ufcg.psoft.mercadofacil.dto.pizza.PizzaDTO;
import com.ufcg.psoft.mercadofacil.dto.pizza.PizzaGetRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.pizza.PizzaRemoveRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.pizza.PizzaPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.model.*;
import com.ufcg.psoft.mercadofacil.notifica.NotificadorSource;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import com.ufcg.psoft.mercadofacil.repository.PizzaRepository;
import com.ufcg.psoft.mercadofacil.service.estabelecimento.EstabelecimentoRemoverEsperaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.ufcg.psoft.mercadofacil.exception.CustomErrorType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Autowired
    FuncionarioRepository funcionarioRepository;
    @Autowired
    PizzaRepository pizzaRepository;
    @Autowired
    ClienteRepository clienteRepository;

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
                .nome("Sorveteria")
                .espera(new HashSet<Funcionario>())
                .entregadores(new HashSet<>())
                .cardapio(new HashSet<>())
                .notificadorSource(new NotificadorSource())
                .codigoAcesso(123456)
                .build()
        );
        estabelecimento2 = Estabelecimento.builder()
                .nome("Pizzando")
                .codigoAcesso(123458)
                .build();
        estabelecimentoPostRequestDTO = EstabelecimentoPostPutRequestDTO.builder()
                .nome("Estabelecimento Dez")
                .codigoAcesso(123456)
                .build();
        estabelecimentoPutRequestDTO = EstabelecimentoPostPutRequestDTO.builder()
                .nome("Estabelecimento Vinte")
                .codigoAcesso(123458)
                .build();
    }

    @AfterEach
    void tearDown() {
        estabelecimentoRepository.deleteAll();
        funcionarioRepository.deleteAll();
        pizzaRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    @Test
    @DisplayName("Quando criamos um novo estabelecimento com dados válidos")
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

        EstabelecimentoDTO estabelecimentoResultante = objectMapper.readValue(responseJsonString, EstabelecimentoDTO.EstabelecimentoDTOBuilder.class).build();

        // Assert
        assertAll(
                () -> assertNotNull(estabelecimentoResultante.getId()),
                () -> assertEquals(estabelecimentoPostRequestDTO.getNome(), estabelecimentoResultante.getNome())
        );
    }

    @Test
    @DisplayName("Quando alteramos um estabelecimento com nome válido")
    void quandoAlteramosUmEstabelecimentoValido() throws Exception {
        // Arrange
        EstabelecimentoNomePatchRequestDTO estabelecimentoNomePatchRequestDTO = EstabelecimentoNomePatchRequestDTO.builder()
                .nome("Padaria")
                .codigoAcesso(123456)
                .build();

        // Act
        String responseJsonString = driver.perform(patch(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/nome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estabelecimentoNomePatchRequestDTO)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        EstabelecimentoDTO estabelecimentoResultante = objectMapper.readValue(responseJsonString, EstabelecimentoDTO.EstabelecimentoDTOBuilder.class).build();

        // Assert
        assertEquals("Padaria", estabelecimentoResultante.getNome());
    }



    @Nested
    @DisplayName("Conjunto de casos de teste RESTApi para pizza")
    class PizzaTests {
        PizzaPostPutRequestDTO pizzaPostPutRequestDTO;

        @BeforeEach
        void setup() {
            pizzaPostPutRequestDTO = PizzaPostPutRequestDTO.builder()
                    .nomePizza("Pizza Baiana")
                    .disponibilidade("disponivel")
                    .tamanho("GRANDE")
                    .sabor(new HashSet<>())
                    .build();
            pizzaPostPutRequestDTO.getSabor().add(Sabor.builder()
                    .nomeSabor("Queijo2")
                    .tipo("Doce")
                    .preco(2.00)
                    .build());
        }

        @Test
        @Transactional
        @DisplayName("Quando um estabelecimento criar uma pizza com dados validos")
        void quandoEstabelecimentoCriaUmaPizza() throws Exception {
            //Arrange
            //Act
            String responseJsonString = driver.perform(post(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pizzaPostPutRequestDTO)))
                    .andExpect(status().isCreated()) // Codigo 201
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            PizzaDTO resultado = objectMapper.readValue(responseJsonString, PizzaDTO.PizzaDTOBuilder.class).build();

            //assertNotNull(resultado.getId());
            //assertEquals(resultado.getSabor().stream().findFirst().get().getNomeSabor(), pizzaPostPutRequestDTO.getNomePizza());
        }

        @Test
        @Transactional
        @DisplayName("Quando um estabelecimento altera os valores de uma pizza")
        void quandoEstabelecimentoAlteraUmaPizza() throws Exception {
            //Arrange
            Sabor sabor = Sabor.builder()
                    .nomeSabor("Mamao")
                    .tipo("Doce")
                    .preco(2.00)
                    .build();
            Pizza pizza = pizzaRepository.save(Pizza.builder()
                    .nomePizza("Pizza Sao jose")
                    .tamanho("GRANDE")
                    .sabor(new HashSet<>())
                    .disponibilidade("disponivel")
                    .build());
            pizza.getSabor().add(sabor);
            estabelecimento.getCardapio().add(pizza);

            PizzaPostPutRequestDTO pizzaPostPutRequestDTO1 = PizzaPostPutRequestDTO.builder()
                    .nomePizza("Pizza Matheus Paraibano")
                    .tamanho("GRANDE")
                    .sabor(new HashSet<>())
                    .disponibilidade("disponivel")
                    .build();
            pizzaPostPutRequestDTO1.getSabor().add(Sabor.builder()
                    .nomeSabor("Queijo")
                    .tipo("Salgada")
                    .preco(4.00)
                    .build());

            //Act
            String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/atualizar_pizza/" + pizza.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pizzaPostPutRequestDTO1)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            PizzaDTO resultado = objectMapper.readValue(responseJsonString, PizzaDTO.PizzaDTOBuilder.class).build();

            //Assert
            assertEquals("Queijo", resultado.getSabor().stream().findFirst().get().getNomeSabor());
            assertEquals("Pizza Matheus Paraibano", resultado.getNomePizza());
        }

        @Test
        @Transactional
        @DisplayName("Quando um estabelecimento remove uma pizza")
        void quandoEstabelecimentoExcluiUmaPizza() throws Exception {
            //Arrange
            Pizza pizza = pizzaRepository.save(Pizza.builder()
                    .nomePizza("Pizza Mane")
                    .tamanho("GRANDE")
                    .sabor(Set.of(new ModelMapper().map(pizzaPostPutRequestDTO, Sabor.class)))
                    .disponibilidade("disponivel")
                    .build());
            estabelecimento.getCardapio().add(pizza);
            PizzaRemoveRequestDTO pizzaRemoveRequestDTO = PizzaRemoveRequestDTO.builder().id(pizza.getId()).build();

            //Arrange
            String responseJsonString = driver.perform(delete(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/remove_pizza")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pizzaRemoveRequestDTO)))
                    .andExpect(status().isNoContent()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            //Assert
            assertTrue(responseJsonString.isBlank());
            assertEquals(0, estabelecimento.getCardapio().size());
        }

        @Test
        @Transactional
        @DisplayName("Quando um estabelecimento buscar uma pizza")
        void quandoEstabelecimentoBuscaPizza() throws Exception {
            //Arrange
            Pizza pizza = pizzaRepository.save(Pizza.builder()
                    .nomePizza("Pizza vai na fe")
                    .tamanho("GRANDE")
                    .sabor(new HashSet<>())
                    .disponibilidade("disponivel")
                    .build());
            estabelecimento.getCardapio().add(pizza);

            PizzaPostPutRequestDTO pizzaPostPutRequestDTO1 = PizzaPostPutRequestDTO.builder()
                    .nomePizza("Pizza Baiana")
                    .tamanho("GRANDE")
                    .sabor(new HashSet<>())
                    .disponibilidade("disponivel")
                    .build();

            Pizza pizza1 = pizzaRepository.save(Pizza.builder()
                    .nomePizza("Pizza lele")
                    .tamanho("GRANDE")
                    .sabor(new HashSet<>())
                    .disponibilidade("disponivel")
                    .build());
            estabelecimento.getCardapio().add(pizza1);
            PizzaGetRequestDTO pizzaGetRequestDTO = PizzaGetRequestDTO.builder().id(pizza1.getId()).build();

            //Arrange
            String responseJsonString = driver.perform(get(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/cardapio")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pizzaGetRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            PizzaDTO resultado = objectMapper.readValue(responseJsonString, PizzaDTO.PizzaDTOBuilder.class).build();

            //Assert
            assertEquals(pizza1.getSabor(), resultado.getSabor());
        }

        @Test
        @Transactional
        @DisplayName("Quando um estabelecimento buscar por todas as pizza (cardapio)")
        void quandoEstabelecimentoListaCardapio() throws Exception {
            //Arrange
            Pizza pizza = pizzaRepository.save(Pizza.builder()
                    .nomePizza("Pizza opa")
                    .tamanho("GRANDE")
                    .sabor(new HashSet<>())
                    .disponibilidade("disponivel")
                    .build());
            estabelecimento.getCardapio().add(pizza);
            PizzaPostPutRequestDTO pizzaPostPutRequestDTO1 = PizzaPostPutRequestDTO.builder()
                    .nomePizza("Pizza Baiana")
                    .disponibilidade("disponivel")
                    .tamanho("GRANDE")
                    .sabor(new HashSet<>())
                    .build();
            Pizza pizza1 = pizzaRepository.save(Pizza.builder()
                    .nomePizza("Pizza vai vai")
                    .tamanho("GRANDE")
                    .sabor(new HashSet<>())
                    .disponibilidade("indisponivel")
                    .build());
            estabelecimento.getCardapio().add(pizza1);
            PizzaPostPutRequestDTO pizzaPostPutRequestDTO2 = PizzaPostPutRequestDTO.builder()
                    .nomePizza("Pizza Baiana")
                    .disponibilidade("disponivel")
                    .tamanho("GRANDE")
                    .sabor(new HashSet<>())
                    .build();
            Pizza pizza2 = pizzaRepository.save(Pizza.builder()
                    .nomePizza("Pizza bola")
                    .tamanho("GRANDE")
                    .sabor(new HashSet<>())
                    .disponibilidade("disponivel")
                    .build());
            estabelecimento.getCardapio().add(pizza2);

            //Arrange
            String responseJsonString = driver.perform(get(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/lista_cardapio")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<PizzaDTO> resultado = objectMapper.readValue(responseJsonString, new TypeReference<List<PizzaDTO>>() {});

            //Assert
            assertEquals(3, resultado.size());
            assertEquals("indisponivel", resultado.get(resultado.size()-1).getDisponibilidade());
        }


    }



    @Nested
    @DisplayName("Conjunto de casos de verificação de pizza")
    class PizzaVerificaCondicoes {
        Pizza pizza;
        Pizza pizza2;
        Cliente cliente;
        Sabor sabor;
        Sabor sabor1;
        ClienteInteressado clienteInteressado;

        @BeforeEach
        void setup() {
            sabor1 = Sabor.builder()
                    .nomeSabor("Frango")
                    .preco(2.00)
                    .tipo("Salgada")
                    .build();

            sabor = Sabor.builder()
                    .nomeSabor("Calabresa")
                    .preco(2.00)
                    .tipo("Salgada")
                    .build();

            pizza = pizzaRepository.save(Pizza.builder()
                    .nomePizza("Pizza Queimada")
                    .tamanho("GRANDE")
                    .disponibilidade("disponivel")
                    .sabor(new HashSet<>())
                    .build());
            pizza.getSabor().add(sabor);
            estabelecimento.getCardapio().add(pizza);

            pizza2 = pizzaRepository.save(Pizza.builder()
                    .nomePizza("Pizza Braba")
                    .tamanho("GRANDE")
                    .disponibilidade("indisponivel")
                    .sabor(new HashSet<>())
                    .build());
            pizza2.getSabor().add(sabor1);
            estabelecimento.getCardapio().add(pizza2);

            cliente = clienteRepository.save(Cliente.builder()
                    .nomeCompleto("Matheus")
                    .build());

            /*clienteInteressado = ClienteInteressado.builder()
                    .id(cliente.getId())
                    .nomeCompleto(cliente.getNomeCompleto())
                    .saborDeInteresse(sabor1.getNome())
                    .build();*/
            //estabelecimento.getInteressados().add(clienteInteressado);
        }

        @Test
        @Transactional
        @DisplayName("Quando um estabelecimento alterar a disponibilidade da pizza para indisponível")
        void quandoEstabelecimentoAlterarParaIndisponivel() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            //Act
            String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/indisponivel/" + pizza.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EstabelecimentoMensagemGetDTO resultado = objectMapper.readValue(responseJsonString, EstabelecimentoMensagemGetDTO.EstabelecimentoMensagemGetDTOBuilder.class).build();

            //Assert
            assertEquals("", resultado.getMensagem());
        }

        @Test
        @Transactional
        @DisplayName("Quando um estabelecimento alterar a disponibilidade da pizza para disponivel")
        void quandoEstabelecimentoAlterarParaDisponivel() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()
            estabelecimento.getNotificadorSource().addInteresse(cliente, pizza2);


            //Act
            String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/disponivel/" + pizza2.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();
            EstabelecimentoMensagemGetDTO resultado = objectMapper.readValue(responseJsonString, EstabelecimentoMensagemGetDTO.EstabelecimentoMensagemGetDTOBuilder.class).build();

            //Assert
            //assertEquals("Matheus, seu sabor de interesse: Frango, esta disponivel\n", resultado.getMensagem());
        }

        @Test
        @Transactional
        @DisplayName("Quando um estabelecimento alterar a disponibilidade da pizza para disponivel com mais de um cliente interessado")
        void quandoEstabelecimentoAlterarParaDisponivelMaisDeUmCliente() throws Exception {
            // Arrange
            Cliente cliente1 = clienteRepository.save(Cliente.builder()
                    .nomeCompleto("Lucas")
                    .build());
            estabelecimento.getNotificadorSource().addInteresse(cliente, pizza2);
            estabelecimento.getNotificadorSource().addInteresse(cliente1, pizza2);

            System.out.println(estabelecimento.toString());


            //Act
            String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/disponivel/" + pizza2.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();
            EstabelecimentoMensagemGetDTO resultado = objectMapper.readValue(responseJsonString, EstabelecimentoMensagemGetDTO.EstabelecimentoMensagemGetDTOBuilder.class).build();

            System.out.println(estabelecimento.toString());

            //Assert
        }

        @Test
        @Transactional
        @DisplayName("Quando um estabelecimento alterar a disponibilidade da pizza para disponivel ja estando disponivel")
        void quandoEstabelecimentoAlterarParaDisponivelEstandoDisponivel() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            //Act
            String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/disponivel/" + pizza.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EstabelecimentoMensagemGetDTO resultado = objectMapper.readValue(responseJsonString, EstabelecimentoMensagemGetDTO.EstabelecimentoMensagemGetDTOBuilder.class).build();

            //Assert
            //assertNull(resultado.getMensagem());
            assertEquals("", resultado.getMensagem());
        }

        @Test
        @Transactional
        @DisplayName("Quando um estabelecimento alterar a disponibilidade da pizza para indisponivel ja estando indisponivel")
        void quandoEstabelecimentoAlterarParaIndisponivelEstandoIndisponivel() throws Exception {
            //Arrange
            // nenhuma necessidade além do setup()

            //Act
            String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/indisponivel/" + pizza2.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EstabelecimentoMensagemGetDTO resultado = objectMapper.readValue(responseJsonString, EstabelecimentoMensagemGetDTO.EstabelecimentoMensagemGetDTOBuilder.class).build();

            //Assert
            //assertNull(resultado.getMensagem());
            assertEquals("", resultado.getMensagem());
        }
    }

    @Nested
    @DisplayName("Casos de teste de validação e verificação de codigo de acesso")
    class CasosTesteValidacaoVerificacao {
        @Test
        @DisplayName("Quando alteramos com o codigo de acesso invalido pelo banco")
        void quandoAlteramosEstabelecimentoComCodigoAcessoInvalidoPeloBanco() throws Exception {
            // Arrange
            EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO = EstabelecimentoPostPutRequestDTO.builder()
                    .codigoAcesso(988989)
                    .nome("Nome")
                    .build();

            // Act
            String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(estabelecimentoPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O codigo de acesso eh diferente!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando alteramos com o codigo de acesso invalido pelo banco")
        void quandoAlteramosNomeEstabelecimentoComCodigoAcessoInvalidoPeloBanco() throws Exception {
            // Arrange
            EstabelecimentoNomePatchRequestDTO estabelecimentoPostPutRequestDTO = EstabelecimentoNomePatchRequestDTO.builder()
                    .codigoAcesso(988989)
                    .nome("Nome")
                    .build();

            // Act
            String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(estabelecimentoPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O codigo de acesso eh diferente!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando alteramos apenas o nome do estabelecimento com dados inválido (em branco)")
        void quandoAlteramosNomeDoEstabelecimentoInvalidoBanco() throws Exception {
            // Arrange
            EstabelecimentoNomePatchRequestDTO estabelecimentoNomePatchRequestDTO = EstabelecimentoNomePatchRequestDTO.builder()
                    .nome("")
                    .build();
            Long id = 40L;

            // Act
            String responseJsonString = driver.perform(patch(URI_ESTABELECIMENTOS + "/" + id + "/nome")
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
                    .codigoAcesso(12456)
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
        @DisplayName("Quando buscamos pelo estabelecimento por id válido mas o codigo de acesso é inválido")
        void quandoBuscamosUmEstabelecimentoVlidoPeloIdComCodigoInvalidoPeloCodigoAcesso() throws Exception {
            // Arrange
            EstabelecimentoGetRequestDTO estabelecimentoGetRequestDTO = EstabelecimentoGetRequestDTO.builder()
                    .codigoAcesso(124599)
                    .build();

            // Act
            String responseJsonString = driver.perform(get(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(estabelecimentoGetRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O codigo de acesso eh diferente!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando tentamos alterar o nome de um estabelecimento por id válido mas o codigo de acesso é inválido")
        void quandoTentamosAlterarNomeEstabelecimentoValidoPeloIdMasPeloCodigoAcessoInvalido() throws Exception {
            // Arrange
            EstabelecimentoNomePatchRequestDTO estabelecimentoNomePatchRequestDTOO = EstabelecimentoNomePatchRequestDTO.builder()
                    .nome("Padaria")
                    .codigoAcesso(124599)
                    .build();

            // Act
            String responseJsonString = driver.perform(patch(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(estabelecimentoNomePatchRequestDTOO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O codigo de acesso eh diferente!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando alteramos apenas o id do estabelecimento com dados inválido (id menor que 6 digitos)")
        void quandoCriamosUmEstabelecimentoComCodigoInvalidoMaiorQueSeisDigitosBanco() throws Exception {
            // Arrange
            EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO = EstabelecimentoPostPutRequestDTO.builder()
                    .nome("Estabelecimento A")
                    .codigoAcesso(1234567)
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
    }

    @Nested
    @DisplayName("Casos de teste para ApiRestFull.")
    class CasosDeTesteApiRestFull {

        EstabelecimentoRemoveRequestDTO estabelecimentoRemoveRequestDTO;
        EstabelecimentoGetRequestDTO estabelecimentoGetRequestDTO;

        @Test
        @Transactional
        @DisplayName("Quando atualizamos um estabelecimento")
        void quandoAtualizamosUmEstabelecimento() throws Exception {
            // Arrange
            EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO = EstabelecimentoPostPutRequestDTO.builder()
                    .nome("Pizzaria B")
                    .codigoAcesso(123456)
                    .build();

            // Act
            String responseJSONString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(estabelecimentoPostPutRequestDTO)))
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            EstabelecimentoDTO estabelecimentoResultante = objectMapper.readValue(responseJSONString, EstabelecimentoDTO.EstabelecimentoDTOBuilder.class).build();
            assertEquals("Pizzaria B", estabelecimentoResultante.getNome());
        }

        @Test
        @DisplayName("Quando excluimos o estabelecimento")
        void quandoExcluimosUmEstabelecimento() throws Exception {
            // Arrange
            estabelecimentoRemoveRequestDTO = EstabelecimentoRemoveRequestDTO.builder()
                    .codigoAcesso(123456)
                    .build();
            // Act
            String responseJsonString = driver.perform(delete(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(estabelecimentoRemoveRequestDTO))
                    )
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
            // nenhuma necessidade além do setup()

            // Act
            String responseJSONString = driver.perform(get(URI_ESTABELECIMENTOS)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            List<EstabelecimentoDTO> estabelecimentos = objectMapper.readValue(responseJSONString, new TypeReference<List<EstabelecimentoDTO>>() {
            });
            EstabelecimentoDTO estabelecimento1 = estabelecimentos.stream().findFirst().orElse(EstabelecimentoDTO.builder().build());

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
        @Transactional
        @DisplayName("Quando buscamos todos os estabelecimentos")
        void quandoBuscamosUmEstabelecimentos() throws Exception {
            // Arrange
            estabelecimentoGetRequestDTO = EstabelecimentoGetRequestDTO.builder()
                    .codigoAcesso(123458)
                    .build();

            // Act
            String responseJSONString = driver.perform(get(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(estabelecimento))
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            EstabelecimentoDTO estabelecimentoResultante = objectMapper.readValue(responseJSONString, EstabelecimentoDTO.EstabelecimentoDTOBuilder.class).build();

            // Assert
            assertEquals(estabelecimento.getNome(), estabelecimentoResultante.getNome());
        }
    }

    @Nested
    @DisplayName("Testes para aceitações dos pedidos no estabelecimento.")
    class TestePedidosAceitacoes {

        Funcionario funcionario;
        EstabelecimentoAceitarPostRequestDTO estabelecimentoAceitarPostRequestDTO;
        FuncionarioSolicitaEntradaPostRequestDTO funcionarioSolicitaEntradaPostRequestDTO;

        @BeforeEach
        void setup2() {
            funcionario = funcionarioRepository.save(Funcionario.builder()
                    .nome("Lucas")
                    .placa("131231")
                    .cor("vermelho")
                    .veiculo("moto")
                    .codigoAcesso(123459)
                    .id(101010L)
                    .build()
            );
            estabelecimentoRepository.save(estabelecimento);
        }

        @Test
        @Transactional
        @DisplayName("Quando um entregador solicita pedido para um estabelecimento")
        void quandoEntregadorSolicitaPedidoEstabelecimento() throws Exception {
            // Arrange
            funcionarioSolicitaEntradaPostRequestDTO = FuncionarioSolicitaEntradaPostRequestDTO.builder()
                    .id(funcionario.getId())
                    .codigoAcesso(123459)
                    .build();
            // Act
            String responseJsonString = driver.perform(post(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/lista-espera/solicitacao")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(funcionarioSolicitaEntradaPostRequestDTO))
                    )
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EstabelecimentoDTO resultado = objectMapper.readValue(responseJsonString, EstabelecimentoDTO.EstabelecimentoDTOBuilder.class).build();

            // Assert
            assertEquals(1, resultado.getEspera().size());
        }

        @Test
        @Transactional
        @DisplayName("Quando um entregador solicita pedido para um estabelecimento mas o codigo de acesso é inválido")
        void quandoEntregadorSolicitaPedidoEstabelecimentoMasCodigoAcessoInvalido() throws Exception {
            // Arrange
            funcionarioSolicitaEntradaPostRequestDTO = FuncionarioSolicitaEntradaPostRequestDTO.builder()
                    .id(funcionario.getId())
                    .codigoAcesso(123457)
                    .build();
            // Act
            String responseJsonString = driver.perform(post(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/lista-espera/solicitacao")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(funcionarioSolicitaEntradaPostRequestDTO))
                    )
                    .andExpect(status().isBadRequest()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O codigo de acesso eh diferente!", resultado.getMessage());
        }

        @Test
        @Transactional
        @DisplayName("Quando um entregador solicita pedido para um estabelecimento")
        void quandoAceitaPedidoFuncionarioMasNaoEstaNaEsperaEstabelecimento() throws Exception {
            // Arrange
            estabelecimentoAceitarPostRequestDTO = EstabelecimentoAceitarPostRequestDTO.builder()
                    .codigoAcesso(123456)
                    .build();

            // Act
            String responseJsonString = driver.perform(post(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/lista-espera/" + funcionario.getId() + "/aprovacao")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(estabelecimentoAceitarPostRequestDTO))
                    )
                    .andExpect(status().isBadRequest()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O estabelecimento nao contem esse funcionario na lista de espera!", resultado.getMessage());
        }

        @Test
        @Transactional
        @DisplayName("Quando um entregador solicita pedido para um estabelecimento")
        void quandoAceitaPedidoFuncionarioEstaEsperaEstabelecimento() throws Exception {
            // Arrange
            estabelecimentoAceitarPostRequestDTO = EstabelecimentoAceitarPostRequestDTO.builder()
                    .codigoAcesso(123456)
                    .build();
            estabelecimento.getEspera().add(funcionario);

            // Act
            String responseJsonString = driver.perform(post(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/lista-espera/" + funcionario.getId() + "/aprovacao")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(estabelecimentoAceitarPostRequestDTO))
                    )
                    .andExpect(status().isOk()) // Codigo 200
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EstabelecimentoDTO resultado = objectMapper.readValue(responseJsonString, EstabelecimentoDTO.EstabelecimentoDTOBuilder.class).build();

            // Assert
            assertEquals(0, resultado.getEspera().size());
            assertEquals(1, resultado.getEntregadores().size());
        }

        @Test
        @DisplayName("Quando tentamos aceitar um entregador do estabelecimento por id válido mas o codigo de acesso é inválido")
        void quandoTentamosAceitarEntregadorEstabelecimentoValidoPeloIdMasPeloCodigoAcessoInvalido() throws Exception {
            // Arrange
            EstabelecimentoAceitarPostRequestDTO estabelecimentoAceitarPostRequestDTO = EstabelecimentoAceitarPostRequestDTO.builder()
                    .codigoAcesso(124999)
                    .build();
            estabelecimento.getEspera().add(funcionario);

            // Act
            String responseJsonString = driver.perform(post(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/lista-espera/" + funcionario.getId() + "/aprovacao")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(estabelecimentoAceitarPostRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O codigo de acesso eh diferente!", resultado.getMessage());
        }
    }

    @Nested
    @DisplayName("Casos de teste para remoção de entregadores aceitos ou não do estabelecimento")
    class CasosTesteRemocaoEntregador {

        @Autowired
        EstabelecimentoRemoverEsperaService estabelecimentoRemoverEsperaService;

        Funcionario funcionario10;
        Estabelecimento estabelecimento2;
        Long resultEstabelecimento;
        EstabelecimentoRemoveRequestDTO estabelecimentoRemoveRequestDTO;

        @BeforeEach
        void setup() {
            funcionario10 = funcionarioRepository.save(Funcionario.builder()
                    .nome("Lucas")
                    .placa("12344444")
                    .cor("vermelho")
                    .veiculo("carro")
                    .build()
            );

            estabelecimento2 = Estabelecimento.builder()
                    .nome("Sorveteria")
                    .codigoAcesso(123458)
                    .espera(new HashSet<Funcionario>())
                    .entregadores(new HashSet<>())
                    .cardapio(new HashSet<>())
                    .build();

            estabelecimento2.getEspera().add(funcionario10);
            resultEstabelecimento = estabelecimentoRepository.save(estabelecimento2).getId();
        }

        @Test
        @Transactional
        @DisplayName("Quando remove um enregador da lista de espera")
        void quandoRejeitoEntregadorEspera() throws Exception {
            // Arrange
            estabelecimentoRemoveRequestDTO = EstabelecimentoRemoveRequestDTO.builder()
                    .codigoAcesso(123458)
                    .build();

            // Act
            String responseJSONString = driver.perform(delete(URI_ESTABELECIMENTOS + "/" + estabelecimento2.getId() + "/lista-espera/" + funcionario10.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(estabelecimentoRemoveRequestDTO))
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EstabelecimentoDTO response = objectMapper.readValue(responseJSONString, EstabelecimentoDTO.EstabelecimentoDTOBuilder.class).build();

            // Assert
            assertEquals(0, response.getEspera().size());
        }

        @Test
        @Transactional
        @DisplayName("Quando remove um enregador da lista de espera mas o codigo de acesso do estabelecimento é inválido")
        void quandoRejeitoEntregadorEsperaMasCodigoAcessoEhInvalido() throws Exception {
            // Arrange
            estabelecimentoRemoveRequestDTO = EstabelecimentoRemoveRequestDTO.builder().codigoAcesso(123457)
                        .build();

            // Act
            String responseJSONString = driver.perform(delete(URI_ESTABELECIMENTOS + "/" + estabelecimento2.getId() + "/lista-espera/" + funcionario10.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(estabelecimentoRemoveRequestDTO))
                    )
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("O codigo de acesso eh diferente!", resultado.getMessage());
        }

        @Test
        @Transactional
        @DisplayName("Quando tento remove um enregador da lista de espera mas que não esta na lista de espra.")
        void quandoRejeitoEntregadorEsperaMasOentregadorNaoEstaNaEspera() throws Exception {
            // Arrange
            estabelecimentoRemoveRequestDTO = EstabelecimentoRemoveRequestDTO.builder()
                    .codigoAcesso(123456)
                    .build();
            // Act
            String responseJSONString = driver.perform(delete(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/lista-espera/" + funcionario10.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(estabelecimentoRemoveRequestDTO))
                    )
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("O estabelecimento nao contem esse funcionario na lista de espera!", resultado.getMessage());
        }

        @Test
        @Transactional
        @DisplayName("Quando remove um entregador da lista de espera mas o codigo de acesso eh invalido")
        void quandoTentamosRejeitarEntregadorEsperaMasCodigoAcessoEhInvalido() throws Exception {
            // Arrange
            estabelecimentoRemoveRequestDTO = EstabelecimentoRemoveRequestDTO.builder()
                    .codigoAcesso(123457)
                    .build();
            // Act
            String responseJSONString = driver.perform(delete(URI_ESTABELECIMENTOS + "/" + estabelecimento2.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(estabelecimentoRemoveRequestDTO))
                    )
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("O codigo de acesso eh diferente!", resultado.getMessage());
        }
    }
}
