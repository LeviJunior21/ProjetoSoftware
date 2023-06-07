package com.ufcg.psoft.mercadofacil.dto.pedido;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.model.Pizza;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    @JsonProperty("enderecoEntrega")
    private String enderecoEntrega;

    @JsonProperty("valorPedido")
    private Double valorPedido;

    @JsonProperty("pizzas")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Pizza> pizzas = new HashSet<>();

    @JsonProperty("metodoPagamento")
    private String metodoPagamento;
}
