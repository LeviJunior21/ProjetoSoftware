package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.estados.*;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Casos para cliente fazer o pedido")
public class PedidosV1ControllerTests {

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
                    .carrinho(pedido)
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

        ObjectMapper objectMapper = new ObjectMapper();
        Pedido pedido;
        Cliente cliente;
        Pizza pizza;
        Sabor sabor;
        Estabelecimento estabelecimento;
        ClientePedidoRequestDTO clientePedidoRequestDTO;
        String URI_CLIENT = "/v1/clientes";

        @BeforeEach
        void setup() {
            objectMapper.registerModule(new JavaTimeModule());
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
                    .carrinho(pedido)
                    .build();

            pedido = Pedido.builder()
                    .valorPedido(10.00)
                    .enderecoEntrega(cliente.getEnderecoPrincipal())
                    .metodoPagamento("PIX")
                    .pizzas(new HashSet<Pizza>())
                    .build();
            pedido.getPizzas().add(pizza);
            cliente = clienteRepository.save(cliente);
            estabelecimento = estabelecimentoRepository.save(Estabelecimento.builder()
                    .nome("Sorveteria")
                    .espera(new HashSet<Funcionario>())
                    .entregadores(new HashSet<>())
                    .cardapio(new HashSet<>())
                    .notificadorSource(new NotificadorSource())
                    .pedidos(new HashSet<>())
                    .codigoAcesso(123456)
                    .build()
            );

        }

        @AfterEach
        void tearDown() {
            clienteRepository.deleteAll();
            estabelecimentoRepository.deleteAll();
        }

        @Test
        @DisplayName("Quando enviamos um pedido ao estabelecimento e verificamos o estado dele")
        void quandoEnviamosUmPedidoAoEstabelecimentoEVerificamosSeuEstado() throws Exception {
            // Arrange
            clientePedidoRequestDTO = ClientePedidoRequestDTO.builder()
                    .metodoPagamento("PIX")
                    .codigoAcesso(cliente.getCodigoAcesso())
                    .endereco("Rua de Campina Grande")
                    .carrinho(pedido)
                    .build();
            String responseJSONString = driver.perform(post(URI_CLIENT + "/" + cliente.getId() + "/solicitar-pedido/" + estabelecimento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(clientePedidoRequestDTO)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            // Act
            EstabelecimentoDTO estabelecimentoDTO = objectMapper.readValue(responseJSONString, EstabelecimentoDTO.EstabelecimentoDTOBuilder.class).build();

            // Assert
            assertEquals(PedidoRecebido.class, estabelecimentoDTO.getPedidos().stream().findFirst().get().getPedidoStateNext().getClass());
        }
    }
}
