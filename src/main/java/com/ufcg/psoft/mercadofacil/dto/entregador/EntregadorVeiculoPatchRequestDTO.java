package com.ufcg.psoft.mercadofacil.dto.entregador;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class EntregadorVeiculoPatchRequestDTO {
    @JsonProperty("veiculo")
    @NotBlank(message = "Veiculo obrigatoio")
    private String veiculo;
}
