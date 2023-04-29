package com.ufcg.psoft.mercadofacil.dto.entregador;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class EntregadorCorPatchRequestDTO {
    @JsonProperty("cor")
    @NotBlank(message = "Cor do carro obrigatorio")
    private String cor;
}
