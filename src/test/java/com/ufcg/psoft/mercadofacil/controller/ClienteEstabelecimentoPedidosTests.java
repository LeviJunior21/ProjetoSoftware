package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoPostDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostGetRequestDTO;
import com.ufcg.psoft.mercadofacil.estados.*;
import com.ufcg.psoft.mercadofacil.model.*;
import com.ufcg.psoft.mercadofacil.notifica.notificaInteresse.NotificadorSource;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import com.ufcg.psoft.mercadofacil.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Casos para cliente fazer o pedido")
public class ClienteEstabelecimentoPedidosTests {

    @Autowired
    MockMvc driver;

    @Nested
    @DisplayName("Casos de testes unitários da interface quando o cliente faz o pedido: Mudando o estado")
    class casosDeAlteracaoDTO {
        Pedido pedido;
        Cliente cliente;
        Pizza pizza;
        Sabor sabor;

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

            cliente = Cliente.builder()
                    .nomeCompleto("Levi de Lima Pereira Junior")
                    .enderecoPrincipal("Rua de Queimadas")
                    .codigoAcesso(123456)
                    .pedido(pedido)
                    .build();

            pedido = Pedido.builder()
                    .valorPedido(10.00)
                    .enderecoEntrega(cliente.getEnderecoPrincipal())
                    .metodoPagamento("PIX")
                    .pizzas(new HashSet<Pizza>())
                    .build();
            pedido.getPizzas().add(pizza);

        }

        @Test
        @DisplayName("Quando o usuario está fazendo o pedido mas que ainda não enviou ao estabelecimento.")
        void quandoUsuarioFazPedidoMasNaoEnviaAoEstabelecimento() throws Exception {
            assertEquals(CriandoPedido.class, pedido.getPedidoStateNext().getClass());
        }

        @Test
        @DisplayName("Teste de estado anterior quando o usuario está fazendo o pedido mas que ainda não enviou ao estabelecimento.")
        void testeDeEstadoCancelamentoQuandoUsuarioFazPedidoMasNaoEnviaAoEstabelecimento() throws Exception {
            pedido.cancelarPedido();
            assertEquals(CriandoPedido.class, pedido.getPedidoStateNext().getClass());
        }

        @Test
        @DisplayName("Quando o usuario muda de estado para pedido recebido.")
        void testeDeCancelamentoAposAlterarmosEstadoCriandoPedidoParaPedidoRecebido() throws Exception {
            pedido.next();
            pedido.cancelarPedido();
            assertEquals(CriandoPedido.class, pedido.getPedidoStateNext().getClass());
        }

        @Test
        @DisplayName("Quando o usuario muda de estado para pedido recebido.")
        void quandoAlteramosEstadoCriandoPedidoParaPedidoRecebido() throws Exception {
            pedido.next();
            assertEquals(PedidoRecebido.class, pedido.getPedidoStateNext().getClass());
        }

        @Test
        @DisplayName("Quando o usuario está fazendo o pedido mas que ainda não enviou ao estabelecimento.")
        void testeDeQuandoCancelamosPedidoQuandoAlteramosEstadoPedidoRecebidoParaPedidoEmPreparo() throws Exception {
            pedido.next();
            pedido.next();
            pedido.cancelarPedido();
            assertEquals(PedidoEmPreparo.class, pedido.getPedidoStateNext().getClass());
        }

        @Test
        @DisplayName("Quando o usuario está fazendo o pedido mas que ainda não enviou ao estabelecimento.")
        void quandoAlteramosEstadoPedidoRecebidoParaPedidoEmPreparo() throws Exception {
            pedido.next();
            pedido.next();
            assertEquals(PedidoEmPreparo.class, pedido.getPedidoStateNext().getClass());
        }

        @Test
        @DisplayName("Quando o usuario muda de estado.")
        void quandoAlteramosEstadoPedidoEmPreparoParaPedidoPronto() throws Exception {
            pedido.next();
            pedido.next();
            pedido.next();
            assertEquals(PedidoPronto.class, pedido.getPedidoStateNext().getClass());
        }

        @Test
        @DisplayName("Quando o usuario muda de estado.")
        void testDeCancelamentoQuandoAlteramosEstadoPedidoEmPreparoParaPedidoPronto() throws Exception {
            pedido.next();
            pedido.next();
            pedido.next();
            pedido.cancelarPedido();
            assertEquals(PedidoPronto.class, pedido.getPedidoStateNext().getClass());
        }

        @Test
        @DisplayName("Quando o usuario está fazendo o pedido mas que ainda não enviou ao estabelecimento.")
        void quandoAlteramosEstadoPedidoProntoParaPedidoEmRota() throws Exception {
            pedido.next();
            pedido.next();
            pedido.next();
            pedido.next();
            assertEquals(PedidoEmRota.class, pedido.getPedidoStateNext().getClass());
        }

        @Test
        @DisplayName("Quando o usuario está fazendo o pedido mas que ainda não enviou ao estabelecimento.")
        void testeDeCancelamentoQuandoAlteramosEstadoPedidoProntoParaPedidoEmRota() throws Exception {
            pedido.next();
            pedido.next();
            pedido.next();
            pedido.next();
            pedido.cancelarPedido();
            assertEquals(PedidoEmRota.class, pedido.getPedidoStateNext().getClass());
        }

        @Test
        @DisplayName("Quando o usuario muda de estado.")
        void quandoAlteramosEstadoPedidoEmRotaParaPedidoEntregue() throws Exception {
            pedido.next();
            pedido.next();
            pedido.next();
            pedido.next();
            pedido.next();
            assertEquals(PedidoEntregue.class, pedido.getPedidoStateNext().getClass());
        }

        @Test
        @DisplayName("Quando o usuario muda de estado.")
        void testeDeCancelamentoQuandoAlteramosEstadoPedidoEmRotaParaPedidoEntregue() throws Exception {
            pedido.next();
            pedido.next();
            pedido.next();
            pedido.next();
            pedido.next();
            pedido.cancelarPedido();
            assertEquals(PedidoEntregue.class, pedido.getPedidoStateNext().getClass());
        }

        @Test
        @DisplayName("Quando o usuario muda de estado.")
        void quandoAlteramosEstadoPedidoRecebidoParacriandoPedido() throws Exception {
            pedido.next();
            pedido.cancelarPedido();
            assertEquals(CriandoPedido.class, pedido.getPedidoStateNext().getClass());
        }
    }

    @Nested
    @DisplayName("Casos de teste de envio de pedidos ao estabelecimento")
    class CasosTesteEnvioPedidosEstabelecimento {

        @Autowired
        ClienteRepository clienteRepository;
        @Autowired
        EstabelecimentoRepository estabelecimentoRepository;
        @Autowired
        PedidoRepository pedidoRepository;

        ObjectMapper objectMapper = new ObjectMapper();
        Pedido pedido;
        Cliente cliente;
        Pizza pizza;
        Sabor sabor;
        Estabelecimento estabelecimento;
        ClientePedidoRequestDTO clientePedidoRequestDTO;
        Entregador entregador;
        String URI_CLIENT = "/v1/clientes";

        @BeforeEach
        void setup() {
            objectMapper.registerModule(new JavaTimeModule());
            cliente = Cliente.builder()
                    .nomeCompleto("Desgraçado")
                    .enderecoPrincipal("Rua de Queimadas")
                    .codigoAcesso(654321)
                    .pedido(new Pedido())
                    .build();
            clienteRepository.save(cliente);

            entregador = Entregador.builder().nome("fabio da motoca")
                    .veiculo("MOTO")
                    .placa("sssdadez")
                    .cor("vermei").entregando(false)
                    .build();
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
                    .idCliente(cliente.getId())
                    .enderecoEntrega(cliente.getEnderecoPrincipal())
                    .metodoPagamento("PIX")
                    .pizzas(new HashSet<Pizza>())
                    .build();
            pedido.getPizzas().add(pizza);
            pedidoRepository.save(pedido);

            cliente.setPedido(pedido);
            clienteRepository.save(cliente);

            estabelecimento = estabelecimentoRepository.save(Estabelecimento.builder()
                    .nome("Pizzaria do Carlos")
                    .espera(new HashSet<Funcionario>())
                    .entregadores(new HashSet<>())
                    .cardapio(new HashSet<>())
                    .notificadorSource(new NotificadorSource())
                    .pedidos(new HashSet<>())
                    .codigoAcesso(123456)
                    .build());

            //pedido.setIdCliente(cliente.getId());
            //pedidoRepository.save(pedido);
            //pedido.setIdEstabelecimento(estabelecimento.getId());

            estabelecimento.getPedidos().add(pedido);
            estabelecimento.getEntregadores().add(entregador);
            estabelecimentoRepository.save(estabelecimento);
        }

        @AfterEach
        void tearDown() {
            clienteRepository.deleteAll();
            estabelecimentoRepository.deleteAll();
            pedidoRepository.deleteAll();
        }

        @Test
        @DisplayName("Quando enviamos um pedido ao estabelecimento e verificamos o estado dele")
        void quandoEnviamosUmPedidoAoEstabelecimentoEVerificamosSeuEstado() throws Exception {
            // Arrange
            EstabelecimentoPostGetRequestDTO estabelecimentoPostGetRequestDTO = EstabelecimentoPostGetRequestDTO.builder()
                    .codigoAcesso(estabelecimento.getCodigoAcesso())
                    .build();

            String responseJSONString = driver.perform(post( "/v1/estabelecimentos/" + estabelecimento.getId()
                            + "/recebido/" + estabelecimento.getPedidos().stream().findFirst().get().getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(estabelecimentoPostGetRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isNoContent())
                    .andReturn().getResponse().getContentAsString();

            // Act
            // Assert
            assertEquals(PedidoRecebido.class, estabelecimento.getPedidos().stream().findFirst().get().getPedidoStateNext().getClass());
        }

        @Test
        @DisplayName("Quando enviamos um pedido ao estabelecimento e verificamos o estado dele")
        void quandoUmPedidoEstaEmPreparo() throws Exception {
            // Arrange
            EstabelecimentoPostGetRequestDTO estabelecimentoPostGetRequestDTO = EstabelecimentoPostGetRequestDTO.builder()
                    .codigoAcesso(estabelecimento.getCodigoAcesso())
                    .build();

            estabelecimento.getPedidos().stream().findFirst().get().next(); // Recebido

            String responseJSONString = driver.perform(post( "/v1/estabelecimentos/" + estabelecimento.getId()
                            + "/em-preparo/" + estabelecimento.getPedidos().stream().findFirst().get().getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(estabelecimentoPostGetRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isNoContent())
                    .andReturn().getResponse().getContentAsString();

            // Act
            // Assert
            assertEquals(PedidoEmPreparo.class, estabelecimento.getPedidos().stream().findFirst().get().getPedidoStateNext().getClass());
        }

        @Test
        @DisplayName("Quando enviamos um pedido ao estabelecimento e verificamos o estado dele")
        void quandoUmPedidoEstaPronto() throws Exception {
            // Arrange
            EstabelecimentoPostGetRequestDTO estabelecimentoPostGetRequestDTO = EstabelecimentoPostGetRequestDTO.builder()
                    .codigoAcesso(estabelecimento.getCodigoAcesso())
                    .build();

            estabelecimento.getPedidos().stream().findFirst().get().next(); // Recebido
            estabelecimento.getPedidos().stream().findFirst().get().next(); // EmPreparo

            String responseJSONString = driver.perform(post( "/v1/estabelecimentos/" + estabelecimento.getId()
                            + "/pronto/" + estabelecimento.getPedidos().stream().findFirst().get().getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(estabelecimentoPostGetRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isNoContent())
                    .andReturn().getResponse().getContentAsString();

            // Act
            // Assert
            assertEquals(PedidoPronto.class, estabelecimento.getPedidos().stream().findFirst().get().getPedidoStateNext().getClass());
        }

        @Test
        @DisplayName("Quando o pedido está em rota")
        void quandoUmPedidoEstaEmRota() throws Exception {
            EstabelecimentoPostGetRequestDTO estabelecimentoPostGetRequestDTO = EstabelecimentoPostGetRequestDTO.builder()
                    .codigoAcesso(estabelecimento.getCodigoAcesso())
                    .build();

            estabelecimento.getPedidos().stream().findFirst().get().next(); // Recebido
            estabelecimento.getPedidos().stream().findFirst().get().next(); // EmPreparo
            estabelecimento.getPedidos().stream().findFirst().get().next(); // Pronto

            String responseJSONString = driver.perform(post( "/v1/estabelecimentos/" + estabelecimento.getId() + "/em-rota/"
                            + cliente.getId() + "/" + estabelecimento.getPedidos().stream().findFirst().get().getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(estabelecimentoPostGetRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isNoContent())
                    .andReturn().getResponse().getContentAsString();

            assertEquals(PedidoEmRota.class, estabelecimento.getPedidos().stream().findFirst().get().getPedidoStateNext().getClass());
        }

        @Test
        @DisplayName("Quando um pedido foi entregue com sucesso")
        void quandoUmPedidoFoiEntregue() throws Exception {
            // Arrange
            ClientePedidoPostDTO clientePedidoPostDTO = ClientePedidoPostDTO.builder()
                    .codigoAcesso(cliente.getCodigoAcesso())
                    .build();

            // Quando um pedido eh adicionado direto no estabelecimento, ele entra no estado "CriandoPedido"
                                                                            // ESTADOS:
            estabelecimento.getPedidos().stream().findFirst().get().next(); // Recebido
            estabelecimento.getPedidos().stream().findFirst().get().next(); // EmPreparo
            estabelecimento.getPedidos().stream().findFirst().get().next(); // Pronto
            estabelecimento.getPedidos().stream().findFirst().get().next(); // EmRota

            // Act
            String responseJSONString = driver.perform(post( URI_CLIENT + "/" + cliente.getId()
                            + "/entregue/" + estabelecimento.getId() + "/" + estabelecimento.getPedidos().stream().findFirst().get().getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePedidoPostDTO)))
                    .andDo(print())
                    .andExpect(status().isNoContent())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertEquals(PedidoEntregue.class, estabelecimento.getPedidos().stream().findFirst().get().getPedidoStateNext().getClass());
        }
    }
}
