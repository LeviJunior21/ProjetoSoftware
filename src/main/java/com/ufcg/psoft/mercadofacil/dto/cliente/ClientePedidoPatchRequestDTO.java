package com.ufcg.psoft.mercadofacil.dto.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.dto.valid.estabelecimento.CodigoAcesso;
import com.ufcg.psoft.mercadofacil.estados.PedidoState;
import com.ufcg.psoft.mercadofacil.model.Pedido;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientePedidoPatchRequestDTO {
    @JsonProperty("codigoAcesso")
    @CodigoAcesso
    private Integer codigoAcesso;

    @JsonProperty("carrinho")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @NotNull(message = "Carrinho null invalido")
    private Pedido carrinho;
}
