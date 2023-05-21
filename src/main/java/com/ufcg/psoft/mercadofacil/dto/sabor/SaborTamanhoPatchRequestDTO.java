package com.ufcg.psoft.mercadofacil.dto.sabor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.dto.valid.pizza.TamanhoPizza;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaborTamanhoPatchRequestDTO {
    @JsonProperty("tamanho")
    @NotBlank(message = "Tamanho da pizza eh obrigatorio")
    @TamanhoPizza
    private String tamanho;
}