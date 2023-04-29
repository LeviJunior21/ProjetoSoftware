package com.ufcg.psoft.mercadofacil.dto.entregador;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class EntregadorPlacaPatchRequestDTO {
    @JsonProperty("placa")
    @NotBlank(message = "Placa do carro obrigatorio")
    private String placa;
}
