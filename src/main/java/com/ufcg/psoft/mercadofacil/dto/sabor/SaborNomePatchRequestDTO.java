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
public class SaborNomePatchRequestDTO {
    @JsonProperty("nome")
    @NotBlank(message = "Nome eh obrigatorio")
    private String nome;
}