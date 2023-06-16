package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoGetFiltragemDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoPostDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostGetRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.pedido.PedidoDTO;
import com.ufcg.psoft.mercadofacil.estados.*;
import com.ufcg.psoft.mercadofacil.exception.CustomErrorType;
import com.ufcg.psoft.mercadofacil.model.*;
import com.ufcg.psoft.mercadofacil.notifica.notificaInteresse.NotificadorSource;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import com.ufcg.psoft.mercadofacil.repository.EntregadorRepository;
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
import java.util.List;

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
        @Autowired
        EntregadorRepository entregadorRepository;

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
                    .nomeCompleto("Levi")
                    .enderecoPrincipal("Rua de Queimadas")
                    .codigoAcesso(654321)
                    .pedido(new Pedido())
                    .historicoPedidos(new HashSet<>())
                    .build();
            clienteRepository.save(cliente);

            entregador = Entregador.builder().nome("fabio da motoca")
                    .veiculo("MOTO")
                    .placa("sssdadez")
                    .cor("vermei")
                    .entregando(false)
                    .build();
            entregadorRepository.save(entregador);
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
                    .entregador(entregador)
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

            pedido.setIdEstabelecimento(estabelecimento.getId());
            pedidoRepository.save(pedido);

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
        @DisplayName("Quando o pedido é recebido")
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
        @DisplayName("Quando o pedido está em preparo")
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
        @DisplayName("Quando o pedido está em pronto")
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

            // Assert
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
            quandoUmPedidoEstaEmRota();

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
            assertEquals(false, estabelecimento.getEntregadores().stream().findFirst().get().isEntregando());
        }

        @Test
        @DisplayName("Quando um pedido eh cancelado por um cliente")
        public void quandoUmPedidoEhCancelado() throws Exception {
            // Arrange
            ClientePedidoPostDTO clientePedidoPostDTO = ClientePedidoPostDTO.builder()
                    .codigoAcesso(cliente.getCodigoAcesso())
                    .build();

            estabelecimento.getPedidos().stream().findFirst().get().next(); // Recebido
            estabelecimento.getPedidos().stream().findFirst().get().next(); // EmPreparo

            // Act
            String responseJSONString = driver.perform(post( URI_CLIENT + "/"
                            + cliente.getId() + "/" + estabelecimento.getPedidos().stream().findFirst().get().getId()
                            + "/cancelar/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePedidoPostDTO)))
                    .andDo(print())
                    .andExpect(status().isNoContent())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertEquals(0, estabelecimentoRepository.findById(estabelecimento.getId()).get().getPedidos().size());
        }

        @Test
        @DisplayName("Quando um pedido não pode ser cancelado por um cliente")
        public void quandoUmPedidoNaoPodeCancelado() throws Exception {
            // Arrange
            ClientePedidoPostDTO clientePedidoPostDTO = ClientePedidoPostDTO.builder()
                    .codigoAcesso(cliente.getCodigoAcesso())
                    .build();

            estabelecimento.getPedidos().stream().findFirst().get().next(); // Recebido
            estabelecimento.getPedidos().stream().findFirst().get().next(); // EmPreparo
            estabelecimento.getPedidos().stream().findFirst().get().next(); // Pronto

            // Act
            String responseJSONString = driver.perform(post( URI_CLIENT + "/"
                            + cliente.getId() + "/" + estabelecimento.getPedidos().stream().findFirst().get().getId()
                            + "/cancelar/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePedidoPostDTO)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("Alteracao de estado do pedido invalida!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um pedido não pode ser cancelado por um cliente por ser entregue")
        public void quandoUmPedidoNaoPodeCanceladoJaEntregue() throws Exception {
            // Arrange
            ClientePedidoPostDTO clientePedidoPostDTO = ClientePedidoPostDTO.builder()
                    .codigoAcesso(cliente.getCodigoAcesso())
                    .build();

            estabelecimento.getPedidos().stream().findFirst().get().next(); // Recebido
            estabelecimento.getPedidos().stream().findFirst().get().next(); // EmPreparo
            estabelecimento.getPedidos().stream().findFirst().get().next(); // Pronto
            estabelecimento.getPedidos().stream().findFirst().get().next(); // EmRota

            // Act
            String responseJSONString = driver.perform(post( URI_CLIENT + "/"
                            + cliente.getId() + "/" + estabelecimento.getPedidos().stream().findFirst().get().getId()
                            + "/cancelar/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePedidoPostDTO)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("Alteracao de estado do pedido invalida!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente visualiza seu historico")
        public void quandoUmClienteVisualizaSeuHistorico() throws Exception {
            // Arrange
            quandoUmPedidoEstaPronto();
            Pedido pedido2 = Pedido.builder()
                    .valorPedido(20.00)
                    .idCliente(cliente.getId())
                    .idEstabelecimento(estabelecimento.getId())
                    .enderecoEntrega(cliente.getEnderecoPrincipal())
                    .metodoPagamento("PIX")
                    .pizzas(new HashSet<Pizza>())
                    .build();
            pedidoRepository.save(pedido2);
            estabelecimento.getPedidos().add(pedido2);
            estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido2.getId())).findFirst().get().next(); // Recebido
            estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido2.getId())).findFirst().get().next(); // EmPreparo
            estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido2.getId())).findFirst().get().next(); // Pronto
            estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido2.getId())).findFirst().get().next(); // EmRota
            estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido2.getId())).findFirst().get().next(); // Entregue

            estabelecimentoRepository.save(estabelecimento);

            cliente.getHistoricoPedidos().add(pedido2);

            Estabelecimento estabelecimento1 = estabelecimentoRepository.save(Estabelecimento.builder()
                    .nome("Pizzaria do Fabio")
                    .espera(new HashSet<Funcionario>())
                    .entregadores(new HashSet<>())
                    .cardapio(new HashSet<>())
                    .notificadorSource(new NotificadorSource())
                    .pedidos(new HashSet<>())
                    .codigoAcesso(112233)
                    .build());

            Pedido pedido1 = Pedido.builder()
                    .valorPedido(15.00)
                    .idCliente(cliente.getId())
                    .idEstabelecimento(estabelecimento1.getId())
                    .enderecoEntrega(cliente.getEnderecoPrincipal())
                    .metodoPagamento("PIX")
                    .pizzas(new HashSet<Pizza>())
                    .build();
            pedidoRepository.save(pedido1);

            estabelecimento1.getPedidos().add(pedido1);
            estabelecimento1.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido1.getId())).findFirst().get().next(); // Recebido
            estabelecimento1.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido1.getId())).findFirst().get().next(); // EmPreparo
            estabelecimento1.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido1.getId())).findFirst().get().next(); // Pronto
            estabelecimento1.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido1.getId())).findFirst().get().next(); // EmRota

            cliente.getHistoricoPedidos().add(pedido1);
            clienteRepository.save(cliente);

            // Act
            String responseJSONString = driver.perform(post( URI_CLIENT + "/" + cliente.getId() + "/historico/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            List<PedidoDTO> list = objectMapper.readValue(responseJSONString, new TypeReference<List<PedidoDTO>>() {
            });

            // Assert
            assertEquals(2, list.size());
            assertEquals(pedido2.getValorPedido(), list.get(1).getValorPedido());
        }

        @Test
        @DisplayName("Quando um cliente visualiza apenas um pedido")
        public void quandoUmClienteVisualizaUmPedido() throws Exception {
            // Arrange
            quandoUmPedidoEstaPronto();
            Pedido pedido2 = Pedido.builder()
                    .valorPedido(20.00)
                    .idCliente(cliente.getId())
                    .idEstabelecimento(estabelecimento.getId())
                    .enderecoEntrega(cliente.getEnderecoPrincipal())
                    .metodoPagamento("PIX")
                    .pizzas(new HashSet<Pizza>())
                    .build();
            pedidoRepository.save(pedido2);
            estabelecimento.getPedidos().add(pedido2);
            estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido2.getId())).findFirst().get().next(); // Recebido
            estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido2.getId())).findFirst().get().next(); // EmPreparo
            estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido2.getId())).findFirst().get().next(); // Pronto
            estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido2.getId())).findFirst().get().next(); // EmRota
            estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido2.getId())).findFirst().get().next(); // Entregue

            estabelecimentoRepository.save(estabelecimento);
            cliente.getHistoricoPedidos().add(pedido2);
            clienteRepository.save(cliente);

            //Act
            String responseJSONString = driver.perform(post( URI_CLIENT + "/" + cliente.getId() + "/um-pedido/" + pedido2.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            PedidoDTO result = objectMapper.readValue(responseJSONString, PedidoDTO.PedidoDTOBuilder.class).build();

            //Assert
            assertEquals(pedido2.getValorPedido(), result.getValorPedido());
        }

        @Test
        @DisplayName("Quando um cliente visualiza pedidos por filtro")
        public void quandoUmClienteVisualizaPedidosPorFiltro() throws Exception {
            // Arrange
            quandoUmPedidoEstaPronto();
            Pedido pedido1 = Pedido.builder()
                    .valorPedido(15.00)
                    .idCliente(cliente.getId())
                    .idEstabelecimento(estabelecimento.getId())
                    .enderecoEntrega(cliente.getEnderecoPrincipal())
                    .metodoPagamento("PIX")
                    .pizzas(new HashSet<Pizza>())
                    .build();
            pedidoRepository.save(pedido1);
            estabelecimento.getPedidos().add(pedido1);
            estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido1.getId())).findFirst().get().next(); // Recebido
            estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido1.getId())).findFirst().get().next(); // EmPreparo
            estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido1.getId())).findFirst().get().next(); // Pronto
            estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido1.getId())).findFirst().get().next(); // EmRota

            Pedido pedido2 = Pedido.builder()
                    .valorPedido(20.00)
                    .idCliente(cliente.getId())
                    .idEstabelecimento(estabelecimento.getId())
                    .enderecoEntrega(cliente.getEnderecoPrincipal())
                    .metodoPagamento("PIX")
                    .pizzas(new HashSet<Pizza>())
                    .build();
            pedidoRepository.save(pedido2);
            estabelecimento.getPedidos().add(pedido2);
            estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido2.getId())).findFirst().get().next(); // Recebido
            estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido2.getId())).findFirst().get().next(); // EmPreparo
            estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(pedido2.getId())).findFirst().get().next(); // Pronto

            estabelecimentoRepository.save(estabelecimento);
            cliente.getHistoricoPedidos().add(pedido1);
            cliente.getHistoricoPedidos().add(pedido2);
            clienteRepository.save(cliente);

            ClientePedidoGetFiltragemDTO clientePedidoGetFiltragemDTO = ClientePedidoGetFiltragemDTO.builder()
                    .filtro("Pronto")
                    .build();

            // Act
            String responseJSONString = driver.perform(post( URI_CLIENT + "/" + cliente.getId() + "/filtragem")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePedidoGetFiltragemDTO)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            List<PedidoDTO> list = objectMapper.readValue(responseJSONString, new TypeReference<List<PedidoDTO>>() {
            });

            //Assert
            assertEquals(2, list.size());
        }

    }
}
