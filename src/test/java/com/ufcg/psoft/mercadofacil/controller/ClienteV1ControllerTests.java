package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteEnderecoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.CustomErrorType;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
                .carrinhos(new ArrayList<>())
                .build()
        );
        cliente2 = clienteRepository.save(Cliente.builder()
                .nomeCompleto("Lucas de Souza Pereira")
                .enderecoPrincipal("Rua de Campina Grande")
                .codigoAcesso(123456)
                .carrinhos(new ArrayList<>())
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
    @DisplayName("Casos de teste do Data Transfer Object - DTO")
    class casosDeAlteracaoDTO {

        @Test
        @DisplayName("Quando altero nome do usuario")
        void quandoAlteroNomeUsuario() throws Exception {
            //Arrange
            //Act
            String responseJSON = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteNomePatchRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 201
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Cliente cliente = objectMapper.readValue(responseJSON, Cliente.ClienteBuilder.class).build();

            //Assert
            assertEquals(clienteNomePatchRequestDTO.getNomeCompleto(), cliente.getNomeCompleto());
        }

        @Test
        @DisplayName("Quando altero endereco do usuario")
        void quandoAlteroEnderecoUsuario() throws Exception {
            //Arrange
            //Act
            String responseJSON = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/endereco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteEnderecoPatchRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 201
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Cliente cliente = objectMapper.readValue(responseJSON, Cliente.ClienteBuilder.class).build();

            //Assert
            assertEquals(clienteEnderecoPatchRequestDTO.getEnderecoPrincipal(), cliente.getEnderecoPrincipal());
        }

        @Test
        @DisplayName("Quando altero endereco do usuario vazio")
        void quandoAlteroNomeUsuarioInvalido() throws Exception {
            //Arrange
            clienteNomePatchRequestDTO.setNomeCompleto("");

            //Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteNomePatchRequestDTO)))
                    .andExpect(status().isBadRequest()) // Codigo 201
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
            //Arrange
            clienteEnderecoPatchRequestDTO.setEnderecoPrincipal("Rua CG");
            clienteEnderecoPatchRequestDTO.setCodigoAcesso(123458);

            //Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/endereco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteEnderecoPatchRequestDTO)))
                    .andExpect(status().isBadRequest()) // Codigo 201
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            //Assert
            assertEquals("O codigo de acesso eh diferente!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando altero endereco do usuario vazio")
        void quandoAlteroNomeUsuarioComCodigoInvalidoPeloBanco() throws Exception {
            //Arrange
            clienteNomePatchRequestDTO.setCodigoAcesso(123458);

            //Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteNomePatchRequestDTO)))
                    .andExpect(status().isBadRequest()) // Codigo 201
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            // Assert
            assertEquals("O codigo de acesso eh diferente!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando altero endereco usando codigo invalido menor")
        void quandoAlteroUsuarioCodigoInvalidoPeloBanco() throws Exception {
            //Arrange
            ClientePostPutRequestDTO clientePostPutRequestDTO1 = ClientePostPutRequestDTO.builder()
                    .nomeCompleto("Nome")
                    .enderecoPrincipal("Rua S")
                    .codigoAcesso(123489)
                    .build();
            //Act
            String responseJSONString = driver.perform(put(URI_CLIENTE + "/" + cliente1.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePostPutRequestDTO1)))
                    .andExpect(status().isBadRequest()) // Codigo 201
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            //Assert
            assertEquals("O codigo de acesso eh diferente!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando altero endereco do usuario vazio")
        void quandoAlteroEnderecoUsuarioInvalido() throws Exception {
            //Arrange
            clienteEnderecoPatchRequestDTO.setEnderecoPrincipal("");

            //Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/endereco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteEnderecoPatchRequestDTO)))
                    .andExpect(status().isBadRequest()) // Codigo 201
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            //Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("Endereco vazio invalido", resultado.getErrors().get(0));
        }

        @Test
        @DisplayName("Quando altero endereco do usuario null")
        void quandoAlteroNomeUsuarioNullInvalido() throws Exception {
            //Arrange
            clienteNomePatchRequestDTO.setNomeCompleto(null);

            //Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteNomePatchRequestDTO)))
                    .andExpect(status().isBadRequest()) // Codigo 201
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
            //Arrange
            clienteEnderecoPatchRequestDTO.setEnderecoPrincipal(null);

            //Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/endereco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteEnderecoPatchRequestDTO)))
                    .andExpect(status().isBadRequest()) // Codigo 201
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            //Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("Endereco vazio invalido", resultado.getErrors().get(0));
        }

        @Test
        @DisplayName("Quando altero endereco usando codigo invalido menor")
        void quandoAlteroNomeUsuarioCodigoInvalidoMenor() throws Exception {
            //Arrange
            clienteNomePatchRequestDTO = ClienteNomePatchRequestDTO.builder()
                    .nomeCompleto("Lucas")
                    .codigoAcesso(123)
                    .build();

            //Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteNomePatchRequestDTO)))
                    .andExpect(status().isBadRequest()) // Codigo 201
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            //Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("O codigo deve ter 6 digitos", resultado.getErrors().get(0));
        }

        @Test
        @DisplayName("Quando altero endereco usando codigo invalido menor")
        void quandoAlteroEnderecoUsuarioCodigoInvalidoMenor() throws Exception {
            //Arrange
            clienteEnderecoPatchRequestDTO.setEnderecoPrincipal("Rua CG");
            clienteEnderecoPatchRequestDTO.setCodigoAcesso(123);

            //Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/endereco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteEnderecoPatchRequestDTO)))
                    .andExpect(status().isBadRequest()) // Codigo 201
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            //Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("O codigo deve ter 6 digitos", resultado.getErrors().get(0));
        }

        @Test
        @DisplayName("Quando altero endereco usando codigo invalido maior")
        void quandoAlteroNomeUsuarioCodigoInvalidoMaior() throws Exception {
            //Arrange
            clienteNomePatchRequestDTO = ClienteNomePatchRequestDTO.builder()
                    .nomeCompleto("Lucas")
                    .codigoAcesso(1234567)
                    .build();

            //Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/nome")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteNomePatchRequestDTO)))
                    .andExpect(status().isBadRequest()) // Codigo 201
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            //Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("O codigo deve ter 6 digitos", resultado.getErrors().get(0));
        }

        @Test
        @DisplayName("Quando altero endereco usando codigo invalido maior")
        void quandoAlteroEnderecoUsuarioCodigoInvalidoMaior() throws Exception {
            //Arrange
            clienteEnderecoPatchRequestDTO = ClienteEnderecoPatchRequestDTO.builder()
                    .enderecoPrincipal("Rua CG")
                    .codigoAcesso(1234567)
                    .build();

            //Act
            String responseJSONString = driver.perform(patch(URI_CLIENTE + "/" + cliente1.getId() + "/endereco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteEnderecoPatchRequestDTO)))
                    .andExpect(status().isBadRequest()) // Codigo 201
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            //Assert
            assertEquals("Erros de validacao encontrados", resultado.getMessage());
            assertEquals("O codigo deve ter 6 digitos", resultado.getErrors().get(0));
        }
    }

    @Nested
    @DisplayName("Casos de teste da API Rest Full")
    class casosTesteApiRestFull {

        @Test
        @DisplayName("Quando crio um cliente válido")
        void quandoCrioClienteValido() throws Exception {
            //Arrange
            //Act
            String responseJSONString = driver.perform(post(URI_CLIENTE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePostPutRequestDTO)))
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Cliente cliente = objectMapper.readValue(responseJSONString, Cliente.ClienteBuilder.class).build();

            //Assert
            assertEquals("Lucas", cliente.getNomeCompleto());
            assertEquals("Rua de Minas Gerais", cliente.getEnderecoPrincipal());
        }

        @Test
        @DisplayName("Quando atualizo um cliente válido")
        void quandoAtualidoClienteValido() throws Exception {
            //Arrange
            //Act
            String responseJSONString = driver.perform(put(URI_CLIENTE + "/" + cliente2.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clientePostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Cliente cliente = objectMapper.readValue(responseJSONString, Cliente.ClienteBuilder.class).build();

            //Assert
            assertEquals("Lucas", cliente.getNomeCompleto());
            assertEquals("Rua de Minas Gerais", cliente.getEnderecoPrincipal());
        }

        @Test
        @DisplayName("Quando atualizo um cliente válido")
        @Transactional
        void quandoDeletoClienteExistente() throws Exception {
            //Arrange
            //Act
            String responseJSONString = driver.perform(delete(URI_CLIENTE + "/" + cliente2.getId()))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            //Assert
            assertTrue(responseJSONString.isBlank());
            assertEquals(1, clienteRepository.findAll().size());
        }

        @Test
        @DisplayName("Quando pego um cliente pelo ID")
        void quandoPegoClientePeloID() throws Exception {
            //Arrange
            //Act
            String responseJSONString = driver.perform(get(URI_CLIENTE + "/" + cliente2.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            Cliente cliente = objectMapper.readValue(responseJSONString, Cliente.ClienteBuilder.class).build();

            //Assert
            assertEquals("Lucas de Souza Pereira", cliente.getNomeCompleto());
        }

        @Test
        @DisplayName("Quando pego um cliente pelo ID que nao existe")
        void quandoClientePeloIDInexistente() throws Exception {
            //Arrange
            //Act
            String responseJSONString = driver.perform(get(URI_CLIENTE + "/" + 1010L)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJSONString, CustomErrorType.class);

            //Assert
            assertEquals("O cliente consultado nao existe!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando pego um cliente pelo ID")
        void quandoPegoTodosClientes() throws Exception {
            //Arrange
            //Act
            String responseJSONString = driver.perform(get(URI_CLIENTE)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            List<Cliente> clientes = objectMapper.readValue(responseJSONString, new TypeReference<List<Cliente>>() {});
            Cliente cliente = clientes.stream().findFirst().orElse(Cliente.builder().build());

            //Assert
            assertEquals("Levi de Lima Pereira Junior", cliente.getNomeCompleto());
            assertEquals(2, clientes.size());
        }
    }
}