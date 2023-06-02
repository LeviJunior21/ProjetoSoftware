package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.estados.PedidoRecebido;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Pedido;
import com.ufcg.psoft.mercadofacil.model.Pizza;
import com.ufcg.psoft.mercadofacil.model.Sabor;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Casos para cliente fazer o pedido")
public class PedidosV1ControllerTests {
    @Nested
    @DisplayName("Casos de testes da interface quando o cliente faz o pedido: Mudando o estado")
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
        @DisplayName("Quando o usuario muda de estado.")
        void quandoUsuarioFazPedidoEstabelecimento() throws Exception {
            pedido.next();
            assertEquals(PedidoRecebido.class, pedido.getPedidoStateNext().getClass());
        }
    }
}
