package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.*;
import com.ufcg.psoft.mercadofacil.dto.funcionario.FuncionarioPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Funcionario;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import com.ufcg.psoft.mercadofacil.service.estabelecimento.EstabelecimentoRemoverEsperaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.ufcg.psoft.mercadofacil.exception.CustomErrorType;
import java.util.HashSet;
import java.util.List;

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
                .nome("Sorveteria")
                .espera(new HashSet<Funcionario>())
                .entregadores(new HashSet<>())
                .pizzas(new HashSet<>())
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
                .id(estabelecimento.getId())
                .build();

        // Act
        String responseJsonString = driver.perform(patch("/v1/estabelecimentos" + "/alterar_nome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estabelecimentoNomePatchRequestDTO)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        EstabelecimentoDTO estabelecimentoResultante = objectMapper.readValue(responseJsonString, EstabelecimentoDTO.EstabelecimentoDTOBuilder.class).build();

        // Assert
        assertEquals("Padaria", estabelecimentoResultante.getNome());
    }

    @Test
    @DisplayName("Quando alteramos com o codigo de acesso invalido pelo banco")
    void quandoAlteramosEstabelecimentoComCodigoAcessoInvalidoPeloBanco() throws Exception {
        EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO = EstabelecimentoPostPutRequestDTO.builder()
                .codigoAcesso(988989)
                .nome("Nome")
                .build();

        // Act
        String responseJsonString = driver.perform(put("/v1/estabelecimentos/" + estabelecimento.getId() + "/atualizar")
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
        EstabelecimentoNomePatchRequestDTO estabelecimentoPostPutRequestDTO = EstabelecimentoNomePatchRequestDTO.builder()
                .codigoAcesso(988989)
                .nome("Nome")
                .build();

        // Act
        String responseJsonString = driver.perform(put("/v1/estabelecimentos/" + estabelecimento.getId() + "/atualizar")
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

        // Act
        String responseJsonString = driver.perform(patch("/v1/estabelecimentos" + "/alterar_nome")
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
                .id(estabelecimento.getId())
                .codigoAcesso(124599)
                .build();

        // Act
        String responseJsonString = driver.perform(get(URI_ESTABELECIMENTOS + "/estabelecimento")
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
                .id(estabelecimento.getId())
                .nome("Padaria")
                .codigoAcesso(124599)
                .build();

        // Act
        String responseJsonString = driver.perform(patch(URI_ESTABELECIMENTOS + "/alterar_nome")
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

            String responseJSONString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/atualizar")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(estabelecimentoPostPutRequestDTO)))
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EstabelecimentoDTO estabelecimentoResultante = objectMapper.readValue(responseJSONString, EstabelecimentoDTO.EstabelecimentoDTOBuilder.class).build();
            assertEquals("Pizzaria B", estabelecimentoResultante.getNome());
        }
        
        @Test
        @DisplayName("Quando excluimos o estabelecimento")
        void quandoExcluimosUmEstabelecimento() throws Exception {
            estabelecimentoRemoveRequestDTO = EstabelecimentoRemoveRequestDTO.builder()
                    .codigoAcesso(123456)
                    .id(estabelecimento.getId())
                    .build();
            // Arrange
            String responseJsonString = driver.perform(delete(URI_ESTABELECIMENTOS + "/estabelecimento")
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
            // Act
            String responseJSONString = driver.perform(get(URI_ESTABELECIMENTOS)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            List<EstabelecimentoDTO> estabelecimentos = objectMapper.readValue(responseJSONString, new TypeReference<List<EstabelecimentoDTO>>() {});
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
                    .id(estabelecimento.getId())
                    .build();

            // Act
            String responseJSONString = driver.perform(get(URI_ESTABELECIMENTOS + "/estabelecimento")
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

        @Nested
        @DisplayName("Testes para aceitações dos pedidos no estabelecimento.")
        class TestePedidosAceitacoes {

            @Autowired
            FuncionarioRepository funcionarioRepository;
            Funcionario funcionario;
            FuncionarioPostPutRequestDTO funcionarioPostPutRequestDTO;
            EstabelecimentoAceitarRequestDTO estabelecimentoAceitarRequestDTO;
            FuncionarioSolicitaEntradaRequestDTO funcionarioSolicitaEntradaRequestDTO;

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
                //Arrange
                funcionarioSolicitaEntradaRequestDTO = FuncionarioSolicitaEntradaRequestDTO.builder()
                        .id(funcionario.getId())
                        .codigoAcesso(123459)
                        .build();
                //Act
                String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/solicitar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(funcionarioSolicitaEntradaRequestDTO))
                        )
                        .andExpect(status().isOk()) // Codigo 200
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                EstabelecimentoDTO resultado = objectMapper.readValue(responseJsonString, EstabelecimentoDTO.EstabelecimentoDTOBuilder.class).build();

                //Assert
                assertEquals(1, resultado.getEspera().size());
            }

            @Test
            @Transactional
            @DisplayName("Quando um entregador solicita pedido para um estabelecimento mas o codigo de acesso é inválido")
            void quandoEntregadorSolicitaPedidoEstabelecimentoMasCodigoAcessoInvalido() throws Exception {
                //Arrange
                funcionarioSolicitaEntradaRequestDTO = FuncionarioSolicitaEntradaRequestDTO.builder()
                        .id(funcionario.getId())
                        .codigoAcesso(123457)
                        .build();
                //Act
                String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/solicitar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(funcionarioSolicitaEntradaRequestDTO))
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
                //Arrange
                estabelecimentoAceitarRequestDTO = EstabelecimentoAceitarRequestDTO.builder()
                        .codigoAcesso(123456)
                        .id(estabelecimento.getId())
                        .build();

                //Act
                String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + funcionario.getId() + "/aceitar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(estabelecimentoAceitarRequestDTO))
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
                //Arrange
                estabelecimentoAceitarRequestDTO = EstabelecimentoAceitarRequestDTO.builder()
                                .codigoAcesso(123456)
                                .id(estabelecimento.getId())
                                .build();
                estabelecimento.getEspera().add(funcionario);

                //Act
                String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + funcionario.getId() + "/aceitar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(estabelecimentoAceitarRequestDTO))
                        )
                        .andExpect(status().isOk()) // Codigo 200
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                EstabelecimentoDTO resultado = objectMapper.readValue(responseJsonString, EstabelecimentoDTO.EstabelecimentoDTOBuilder.class).build();

                //Assert
                assertEquals(0, resultado.getEspera().size());
                assertEquals(1, resultado.getEntregadores().size());
            }

            @Test
            @DisplayName("Quando tentamos aceitar um entregador do estabelecimento por id válido mas o codigo de acesso é inválido")
            void quandoTentamosAceitarEntregadorEstabelecimentoValidoPeloIdMasPeloCodigoAcessoInvalido() throws Exception {
                // Arrange
                EstabelecimentoAceitarRequestDTO estabelecimentoAceitarRequestDTO = EstabelecimentoAceitarRequestDTO.builder()
                        .id(estabelecimento.getId())
                        .codigoAcesso(124999)
                        .build();
                estabelecimento.getEspera().add(funcionario);

                // Act
                String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + funcionario.getId() + "/aceitar")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(estabelecimentoAceitarRequestDTO)))
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
            @Autowired
            FuncionarioRepository funcionarioRepository;

            Funcionario funcionario10;
            Estabelecimento estabelecimento2;
            Long resultEstabelecimento;

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
                        .pizzas(new HashSet<>())
                        .build();

                estabelecimento2.getEspera().add(funcionario10);
                resultEstabelecimento = estabelecimentoRepository.save(estabelecimento2).getId();
            }

            @Test
            @Transactional
            @DisplayName("Quando remove um enregador da lista de espera")
            void quandoRejeitoEntregadorEspera() throws Exception {
                estabelecimentoRemoveRequestDTO = EstabelecimentoRemoveRequestDTO.builder()
                        .id(estabelecimento2.getId())
                        .codigoAcesso(123458)
                        .build();
                // Arrange
                // Act
                String responseJSONString = driver.perform(delete(URI_ESTABELECIMENTOS + "/" + funcionario10.getId() + "/remover_espera")
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
                estabelecimentoRemoveRequestDTO = EstabelecimentoRemoveRequestDTO.builder()
                        .id(estabelecimento2.getId())
                        .codigoAcesso(123457)
                        .build();
                // Arrange
                // Act
                String responseJSONString = driver.perform(delete(URI_ESTABELECIMENTOS + "/" + funcionario10.getId() + "/remover_espera")
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
                estabelecimentoRemoveRequestDTO = EstabelecimentoRemoveRequestDTO.builder()
                        .id(estabelecimento.getId())
                        .codigoAcesso(123456)
                        .build();
                // Arrange
                // Act
                String responseJSONString = driver.perform(delete(URI_ESTABELECIMENTOS + "/" + funcionario10.getId() + "/remover_espera")
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
                estabelecimentoRemoveRequestDTO = EstabelecimentoRemoveRequestDTO.builder()
                        .id(estabelecimento2.getId())
                        .codigoAcesso(123457)
                        .build();
                // Arrange
                // Act
                String responseJSONString = driver.perform(delete(URI_ESTABELECIMENTOS + "/estabelecimento")
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
}
