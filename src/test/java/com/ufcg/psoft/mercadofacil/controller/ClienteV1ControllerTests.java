package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.mercadofacil.dto.cliente.*;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.exception.CustomErrorType;
import com.ufcg.psoft.mercadofacil.model.*;
import com.ufcg.psoft.mercadofacil.notifica.NotificadorSource;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de Estabelecimentos")
public class ClienteV1ControllerTests {
    @Autowired
    MockMvc driver;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    Cliente cliente1;
    Cliente cliente2;
    ObjectMapper objectMapper = new ObjectMapper();
    ClientePostPutRequestDTO clientePostPutRequestDTO;
    ClienteEnderecoPatchRequestDTO clienteEnderecoPatchRequestDTO;
    ClienteNomePatchRequestDTO clienteNomePatchRequestDTO;

    final String URI_CLIENTE = "/v1/clientes";

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        cliente1 = clienteRepository.save(Cliente.builder()
                .nomeCompleto("Levi de Lima Pereira Junior")
                .enderecoPrincipal("Rua de Queimadas")
                .codigoAcesso(123456)
                .carrinho(new Pedido())
                .build()
        );
        cliente2 = clienteRepository.save(Cliente.builder()
                .nomeCompleto("Lucas de Souza Pereira")
                .enderecoPrincipal("Rua de Campina Grande")
                .codigoAcesso(123458)
                .carrinho(new Pedido())
                .build()
        );

        clienteNomePatchRequestDTO = ClienteNomePatchRequestDTO.builder()
                .nomeCompleto("Levi")
                .codigoAcesso(123456)
                .build();
        clienteEnderecoPatchRequestDTO = ClienteEnderecoPatchRequestDTO.builder()
                .enderecoPrincipal("Sao Paulo")
                .codigoAcesso(123456)
                .build();
        clientePostPutRequestDTO = ClientePostPutRequestDTO.builder()
                .nomeCompleto("Lucas")
                .enderecoPrincipal("Rua de Minas Gerais")
                .codigoAcesso(123456)
                .build();
    }
    @Nested
    @DisplayName("Casos de testes fazendo atualizações usando Data Transfer Object - DTO")
    class casosDeAlteracaoDTO {

        @Test
        @DisplayName("Quando altero nome do usuario")
        void quandoAlteroNomeUsuario() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJSON = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteNomePatchRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            ClienteDTO cliente = objectMapper.readValue(responseJSON, ClienteDTO.ClienteDTOBuilder.class).build();

            // Assert
            assertEquals(clienteNomePatchRequestDTO.getNomeCompleto(), cliente.getNomeCompleto());
            assertEquals(cliente1.getEnderecoPrincipal(), cliente.getEnderecoPrincipal());
            assertEquals(cliente1.getId(), cliente.getId());
        }

        @Test
        @DisplayName("Quando altero endereco do usuario")
        void quandoAlteroEnderecoUsuario() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJSON = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/endereco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteEnderecoPatchRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            ClienteDTO cliente = objectMapper.readValue(responseJSON, ClienteDTO.ClienteDTOBuilder.class).build();

            // Assert
            assertEquals(clienteEnderecoPatchRequestDTO.getEnderecoPrincipal(), cliente.getEnderecoPrincipal());
            assertEquals(cliente1.getNomeCompleto(), cliente.getNomeCompleto());
            assertEquals(cliente1.getId(), cliente.getId());
        }

        @Test
        @DisplayName("Quando altero endereco do usuario vazio")
        void quandoAlteroNomeUsuarioInvalido() throws Exception {
            // Arrange
            clienteNomePatchRequestDTO.setNomeCompleto("");

            // Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteNomePatchRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("Nome vazio invalido", resultado.getErrors().get(0));
        }

        @Test
        @DisplayName("Quando altero endereco usando codigo invalido menor")
        void quandoAlteroEnderecoUsuarioCodigoInvalidoPeloBanco() throws Exception {
            // Arrange
            clienteEnderecoPatchRequestDTO.setEnderecoPrincipal("Rua CG");
            clienteEnderecoPatchRequestDTO.setCodigoAcesso(123458);

            // Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/endereco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteEnderecoPatchRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("O codigo de acesso eh diferente!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando altero endereco do usuario vazio")
        void quandoAlteroNomeUsuarioComCodigoInvalidoPeloBanco() throws Exception {
            // Arrange
            clienteNomePatchRequestDTO.setCodigoAcesso(123458);

            // Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteNomePatchRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("O codigo de acesso eh diferente!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando altero endereco usando codigo invalido menor")
        void quandoAlteroUsuarioCodigoInvalidoPeloBanco() throws Exception {
            // Arrange
            ClientePostPutRequestDTO clientePostPutRequestDTO1 = ClientePostPutRequestDTO.builder()
                    .nomeCompleto("Nome")
                    .enderecoPrincipal("Rua S")
                    .codigoAcesso(123489)
                    .id(cliente1.getId())
                    .build();

            // Act
            String responseJSONString = driver.perform(put(URI_CLIENTE + "/" + cliente1.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePostPutRequestDTO1)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("O codigo de acesso eh diferente!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando altero endereco do usuario vazio")
        void quandoAlteroEnderecoUsuarioInvalido() throws Exception {
            // Arrange
            clienteEnderecoPatchRequestDTO.setEnderecoPrincipal("");

            // Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/endereco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteEnderecoPatchRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("Endereco vazio invalido", resultado.getErrors().get(0));
        }

        @Test
        @DisplayName("Quando altero endereco do usuario null")
        void quandoAlteroNomeUsuarioNullInvalido() throws Exception {
            // Arrange
            clienteNomePatchRequestDTO.setNomeCompleto(null);

            // Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteNomePatchRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("Nome vazio invalido", resultado.getErrors().get(0));
        }

        @Test
        @DisplayName("Quando altero endereco do usuario null")
        void quandoAlteroEnderecoUsuarioNullInvalido() throws Exception {
            // Arrange
            clienteEnderecoPatchRequestDTO.setEnderecoPrincipal(null);

            // Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/endereco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteEnderecoPatchRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("Endereco vazio invalido", resultado.getErrors().get(0));
        }

        @Test
        @DisplayName("Quando altero endereco usando codigo invalido menor")
        void quandoAlteroNomeUsuarioCodigoInvalidoMenor() throws Exception {
            // Arrange
            clienteNomePatchRequestDTO = ClienteNomePatchRequestDTO.builder()
                    .nomeCompleto("Lucas")
                    .codigoAcesso(123)
                    .build();

            // Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteNomePatchRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("O codigo deve ter 6 digitos", resultado.getErrors().get(0));
        }

        @Test
        @DisplayName("Quando altero endereco usando codigo invalido menor")
        void quandoAlteroEnderecoUsuarioCodigoInvalidoMenor() throws Exception {
            // Arrange
            clienteEnderecoPatchRequestDTO.setEnderecoPrincipal("Rua CG");
            clienteEnderecoPatchRequestDTO.setCodigoAcesso(123);

            // Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/endereco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteEnderecoPatchRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("O codigo deve ter 6 digitos", resultado.getErrors().get(0));
        }

        @Test
        @DisplayName("Quando altero endereco usando codigo invalido maior")
        void quandoAlteroNomeUsuarioCodigoInvalidoMaior() throws Exception {
            // Arrange
            clienteNomePatchRequestDTO = ClienteNomePatchRequestDTO.builder()
                    .nomeCompleto("Lucas")
                    .codigoAcesso(1234567)
                    .build();

            // Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteNomePatchRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("O codigo deve ter 6 digitos", resultado.getErrors().get(0));
        }

        @Test
        @DisplayName("Quando altero endereco usando codigo invalido maior")
        void quandoAlteroEnderecoUsuarioCodigoInvalidoMaior() throws Exception {
            // Arrange
            clienteEnderecoPatchRequestDTO = ClienteEnderecoPatchRequestDTO.builder()
                    .enderecoPrincipal("Rua CG")
                    .codigoAcesso(1234567)
                    .build();

            // Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/endereco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteEnderecoPatchRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("O codigo deve ter 6 digitos", resultado.getErrors().get(0));
        }
    }

    @Nested
    @DisplayName("Casos de teste da API Rest Full")
    class casosTesteApiRestFull {
        ClienteGetRequestDTO clienteGetRequestDTO;
        ClienteRemoveRequestDTO clienteRemoveRequestDTO;

        @Test
        @DisplayName("Quando crio um cliente válido")
        void quandoCrioClienteValido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJSONString = driver.perform(post(URI_CLIENTE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePostPutRequestDTO)))
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            ClienteDTO cliente = objectMapper.readValue(responseJSONString, ClienteDTO.ClienteDTOBuilder.class).build();

            // Assert
            assertEquals("Lucas", cliente.getNomeCompleto());
            assertEquals("Rua de Minas Gerais", cliente.getEnderecoPrincipal());
        }

        @Test
        @DisplayName("Quando atualizo um cliente válido")
        void quandoAtualidoClienteValido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJSONString = driver.perform(put(URI_CLIENTE + "/" + cliente1.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            ClienteDTO cliente = objectMapper.readValue(responseJSONString, ClienteDTO.ClienteDTOBuilder.class).build();

            // Assert
            assertEquals("Lucas", cliente.getNomeCompleto());
            assertEquals("Rua de Minas Gerais", cliente.getEnderecoPrincipal());
        }

        @Test
        @DisplayName("Quando atualizo um cliente válido")
        @Transactional
        void quandoDeletoClienteExistente() throws Exception {
            // Arrange
            clienteRemoveRequestDTO = ClienteRemoveRequestDTO.builder()
                    .codigoAcesso(cliente2.getCodigoAcesso())
                    .build();

            // Act
            String responseJSONString = driver.perform(delete(URI_CLIENTE + "/" + cliente2.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteRemoveRequestDTO)))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertTrue(responseJSONString.isBlank());
            assertEquals(1, clienteRepository.findAll().size());
        }

        @Test
        @DisplayName("Quando busco cliente por codigo de acesso invalido")
        void quandoBuscoClientePorCodigoAcessoInvalido() throws Exception {
            // Arrange
            clienteGetRequestDTO = ClienteGetRequestDTO.builder()
                    .codigoAcesso(123459)
                    .build();

            // Act
            String responseJSONString = driver.perform(get(URI_CLIENTE + "/" + cliente2.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteGetRequestDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("O codigo de acesso eh diferente!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando excluo cliente por codigo de acesso invalido")
        void quandoExcluoClientePorCodigoAcessoInvalido() throws Exception {
            // Arrange
            clienteRemoveRequestDTO = ClienteRemoveRequestDTO.builder()
                    .codigoAcesso(123459)
                    .build();

            // Act
            String responseJSONString = driver.perform(delete(URI_CLIENTE + "/" + cliente1.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(clienteRemoveRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("O codigo de acesso eh diferente!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando pego um cliente pelo ID")
        void quandoPegoClientePeloID() throws Exception {
            // Arrange
            clienteGetRequestDTO = ClienteGetRequestDTO.builder()
                    .codigoAcesso(123458)
                    .build();

            // Act
            String responseJSONString = driver.perform(get(URI_CLIENTE + "/" + cliente2.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteGetRequestDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            ClienteDTO cliente = objectMapper.readValue(responseJSONString, ClienteDTO.ClienteDTOBuilder.class).build();

            // Assert
            assertEquals("Lucas de Souza Pereira", cliente.getNomeCompleto());
        }

        @Test
        @DisplayName("Quando pego um cliente pelo ID que nao existe")
        void quandoClientePeloIDInexistente() throws Exception {
            // Arrange
            clienteGetRequestDTO = ClienteGetRequestDTO.builder()
                    .codigoAcesso(123456)
                    .build();
            Long id = 18L;

            // Act
            String responseJSONString = driver.perform(get(URI_CLIENTE + "/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteGetRequestDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("O cliente consultado nao existe!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando pego um cliente pelo ID")
        void quandoPegoTodosClientes() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJSONString = driver.perform(get(URI_CLIENTE)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            List<ClienteDTO> clientes = objectMapper.readValue(responseJSONString, new TypeReference<List<ClienteDTO>>() {});
            ClienteDTO cliente = clientes.stream().findFirst().orElse(ClienteDTO.builder().build());

            // Assert
            assertEquals("Levi de Lima Pereira Junior", cliente.getNomeCompleto());
            assertEquals(2, clientes.size());
        }
    }

    @Nested
    @DisplayName("Casos de teste de realização de pedidos")
    class CasosTesteRealizarPedidos {




        Sabor sabor;
        Pizza pizza;
        Pedido pedido;
        Cliente clienteDez;
        ClientePedidoRequestDTO clientePedidoRequestDTO;
        Estabelecimento estabelecimento;
        @BeforeEach
        void setup() {
            sabor = Sabor.builder()
                    .nomeSabor("Calabreza")
                    .preco(10.00)
                    .tipo("Sei la")
                    .build();

            pizza = Pizza.builder()
                    .nomePizza("Pizza Italiana")
                    .sabor(new HashSet<Sabor>())
                    .disponibilidade("disponivel")
                    .tamanho("GRANDE")
                    .build();
            pizza.getSabor().add(sabor);

            pedido = Pedido.builder()
                    .valorPedido(10.00)
                    .enderecoEntrega(cliente1.getEnderecoPrincipal())
                    .metodoPagamento("PIX")
                    .pizzas(new HashSet<Pizza>())
                    .build();
            pedido.getPizzas().add(pizza);

            clienteDez = Cliente.builder()
                            .nomeCompleto("Levi de Lima Pereira Junior")
                            .enderecoPrincipal("Rua de Queimadas")
                            .codigoAcesso(123456)
                            .carrinho(pedido)
                            .build();

            estabelecimento = Estabelecimento.builder()
                    .nome("Pizzaria Dez")
                    .codigoAcesso(101010)
                    .entregadores(new HashSet<>())
                    .espera(new HashSet<>())
                    .cardapio(new HashSet<>())
                    .notificadorSource(new NotificadorSource())
                    .pedidos(new HashSet<Pedido>())
                    .build();

            clienteDez = clienteRepository.save(clienteDez);
            estabelecimento = estabelecimentoRepository.save(estabelecimento);
        }

        @AfterEach
        void tearDown() {
            estabelecimentoRepository.deleteAll();
            clienteRepository.deleteAll();

        }

        @Test
        @Transactional
        @DisplayName("Quando o usuario faz um pedido ao estabelecimento")
        void quandoUsuarioFazPedidoEstabelecimento() throws Exception {
            clientePedidoRequestDTO = ClientePedidoRequestDTO.builder()
                    .codigoAcesso(clienteDez.getCodigoAcesso())
                    .carrinho(pedido)
                    .metodoPagamento(clienteDez.getCarrinho().getMetodoPagamento())
                    .build();

            String responseJSONString = driver.perform(post(URI_CLIENTE + "/" + clienteDez.getId() + "/solicitar-pedido/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePedidoRequestDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            EstabelecimentoDTO estabelecimentoDTO = objectMapper.readValue(responseJSONString, EstabelecimentoDTO.EstabelecimentoDTOBuilder.class).build();

            assertEquals(1, estabelecimentoDTO.getPedidos().size());
        }

        @Test
        @Transactional
        @DisplayName("Quando o usuario faz um pedido ao estabelecimento mas o código de acesso é diferente")
        void quandoUsuarioFazPedidoEstabelecimentoMasCodigoAcessoEhDiferente() throws Exception {
            clientePedidoRequestDTO = ClientePedidoRequestDTO.builder()
                    .codigoAcesso(101038)
                    .carrinho(pedido)
                    .build();

            String responseJSONString = driver.perform(post(URI_CLIENTE + "/" + clienteDez.getId() + "/solicitar-pedido/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePedidoRequestDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("O codigo de acesso eh diferente!", resultado.getMessage());
        }

        @Test
        @Transactional
        @DisplayName("Quando o usuario faz um pedido ao estabelecimento mas o código de acesso é diferente")
        void quandoUsuarioFazPedidoEstabelecimentoMasOPedidoEhNull() throws Exception {
            clientePedidoRequestDTO = ClientePedidoRequestDTO.builder()
                    .codigoAcesso(101038)
                    .carrinho(null)
                    .build();

            String responseJSONString = driver.perform(post(URI_CLIENTE + "/" + cliente1.getId() + "/solicitar-pedido/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePedidoRequestDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("Carrinho null invalido", resultado.getErrors().get(0));

        }

        @Test
        @Transactional
        @DisplayName("Quando o usuario faz um pedido ao estabelecimento mas o código de acesso é diferente")
        void quandoUsuarioFazPedidoEstabelecimentoMasNaoHaPedidos() throws Exception {
            Pedido pedido2 = Pedido.builder()
                    .valorPedido(10.00)
                    .enderecoEntrega(cliente1.getEnderecoPrincipal())
                    .metodoPagamento("PIX")
                    .pizzas(new HashSet<Pizza>())
                    .build();
            clientePedidoRequestDTO = ClientePedidoRequestDTO.builder()
                    .codigoAcesso(cliente1.getCodigoAcesso())
                    .carrinho(pedido2)
                    .build();

            String responseJSONString = driver.perform(post(URI_CLIENTE + "/" + cliente1.getId() + "/solicitar-pedido/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePedidoRequestDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("Nao ha pizza(s) enviada(s)", resultado.getMessage());
        }

        @Test
        @Transactional
        @DisplayName("Quando o usuario faz um pedido ao estabelecimento mas o cliente não está cadastrado")
        void quandoUsuarioFazPedidoEstabelecimentoMasClienteNaoExiste() throws Exception {
            clientePedidoRequestDTO = ClientePedidoRequestDTO.builder()
                    .codigoAcesso(cliente1.getCodigoAcesso())
                    .carrinho(pedido)
                    .build();
            Long codigoInvalido = 1010L;

            String responseJSONString = driver.perform(post(URI_CLIENTE + "/" + codigoInvalido + "/solicitar-pedido/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePedidoRequestDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("O cliente consultado nao existe!", resultado.getMessage());
        }

        @Test
        @Transactional
        @DisplayName("Quando o usuario faz um pedido ao estabelecimento com metodo de pagamento PIX")
        void quandoUsuarioFazPedidoEstabelecimentoUsandoPIX() throws Exception {
            clientePedidoRequestDTO = ClientePedidoRequestDTO.builder()
                    .codigoAcesso(clienteDez.getCodigoAcesso())
                    .carrinho(pedido)
                    .metodoPagamento(clienteDez.getCarrinho().getMetodoPagamento())
                    .build();

            String responseJSONString = driver.perform(post(URI_CLIENTE + "/" + clienteDez.getId() + "/solicitar-pedido/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePedidoRequestDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            EstabelecimentoDTO estabelecimentoDTO = objectMapper.readValue(responseJSONString, EstabelecimentoDTO.EstabelecimentoDTOBuilder.class).build();
            Pedido pedidoDTO = estabelecimentoDTO.getPedidos().stream().findFirst().get();

            // Assert
            assertEquals(1, estabelecimentoDTO.getPedidos().size());
            assertAll(
                    () -> assertEquals(9.5, pedidoDTO.getValorPedido()),
                    () -> assertEquals("PIX", pedidoDTO.getMetodoPagamento()),
                    () -> assertEquals("Rua de Queimadas", pedidoDTO.getEnderecoEntrega())
            );
        }
        @Test
        @Transactional
        @DisplayName("Quando o usuario faz um pedido ao estabelecimento com metodo de pagamento CREDITO")
        void quandoUsuarioFazPedidoEstabelecimentoUsandoCredito() throws Exception {
            clienteDez.getCarrinho().setMetodoPagamento("CREDITO");
            clientePedidoRequestDTO = ClientePedidoRequestDTO.builder()
                    .codigoAcesso(clienteDez.getCodigoAcesso())
                    .carrinho(pedido)
                    .metodoPagamento(clienteDez.getCarrinho().getMetodoPagamento())
                    .build();

            String responseJSONString = driver.perform(post(URI_CLIENTE + "/" + clienteDez.getId() + "/solicitar-pedido/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePedidoRequestDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            EstabelecimentoDTO estabelecimentoDTO = objectMapper.readValue(responseJSONString, EstabelecimentoDTO.EstabelecimentoDTOBuilder.class).build();
            Pedido pedidoDTO = estabelecimentoDTO.getPedidos().stream().findFirst().get();

            // Assert
            assertEquals(1, estabelecimentoDTO.getPedidos().size());
            assertAll(
                    () -> assertEquals(10.0, pedidoDTO.getValorPedido()),
                    () -> assertEquals("CREDITO", pedidoDTO.getMetodoPagamento()),
                    () -> assertEquals("Rua de Queimadas", pedidoDTO.getEnderecoEntrega())
            );
        }

        @Test
        @Transactional
        @DisplayName("Quando o usuario faz um pedido ao estabelecimento com metodo de pagamento DEBITO")
        void quandoUsuarioFazPedidoEstabelecimentoUsandoDebito() throws Exception {
            clienteDez.getCarrinho().setMetodoPagamento("DEBITO");
            clientePedidoRequestDTO = ClientePedidoRequestDTO.builder()
                    .codigoAcesso(clienteDez.getCodigoAcesso())
                    .carrinho(pedido)
                    .metodoPagamento(clienteDez.getCarrinho().getMetodoPagamento())
                    .build();

            String responseJSONString = driver.perform(post(URI_CLIENTE + "/" + clienteDez.getId() + "/solicitar-pedido/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePedidoRequestDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            EstabelecimentoDTO estabelecimentoDTO = objectMapper.readValue(responseJSONString, EstabelecimentoDTO.EstabelecimentoDTOBuilder.class).build();
            Pedido pedidoDTO = estabelecimentoDTO.getPedidos().stream().findFirst().get();

            // Assert
            assertEquals(1, estabelecimentoDTO.getPedidos().size());
            assertAll(
                    () -> assertEquals(9.75, pedidoDTO.getValorPedido()),
                    () -> assertEquals("DEBITO", pedidoDTO.getMetodoPagamento()),
                    () -> assertEquals("Rua de Queimadas", pedidoDTO.getEnderecoEntrega())
            );
        }
        @Test
        @Transactional
        @DisplayName("Quando cliente tenta realizar um pedido mas o metodo de pagamento eh invalido")
        void quandoMetodoDePagamentoEhInvalido()throws Exception{
            clientePedidoRequestDTO = ClientePedidoRequestDTO.builder()
                    .codigoAcesso(clienteDez.getCodigoAcesso())
                    .carrinho(pedido)
                    .metodoPagamento("PI")
                    .build();

            String responseJSONString = driver.perform(post(URI_CLIENTE + "/" + clienteDez.getId() + "/solicitar-pedido/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePedidoRequestDTO))
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);
            assertEquals("Metodo de pagamento invalido", resultado.getMessage());
        }

    }
}
