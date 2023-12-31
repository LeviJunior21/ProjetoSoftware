package com.ufcg.psoft.mercadofacil.dto.sabor;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaborTipoPatchRequestDTO {
    @JsonProperty("tipo")
    @NotBlank(message = "Tipo da pizza eh obrigatorio")
    private String tipo;
}