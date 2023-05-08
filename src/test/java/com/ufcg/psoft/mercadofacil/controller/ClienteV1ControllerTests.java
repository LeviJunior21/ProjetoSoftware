package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteEnderecoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Funcionario;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .id(123456L)
                .nomeCompleto("Levi de Lima Pereira Junior")
                .endereco("Rua de Queimadas")
                .codigoAcesso(123456)
                .carrinhos(new ArrayList<>())
                .build()
        );
        cliente2 = clienteRepository.save(Cliente.builder()
                .id(123456L)
                .nomeCompleto("Lucas de Souza Pereira")
                .endereco("Rua de Campina Grande")
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
            String responseJSON = driver.perform(patch(URI_CLIENTE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(clienteNomePatchRequestDTO)))
                    .andExpect(status().isCreated()) // Codigo 201
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Cliente cliente = objectMapper.readValue(responseJSON, Cliente.ClienteBuilder.class).build();

            assertEquals(cliente.getNomeCompleto(), clienteNomePatchRequestDTO.getNomeCompleto());
        }
    }
}
