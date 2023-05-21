package com.ufcg.psoft.mercadofacil.dto.produto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoPrecoPatchRequestDTO {
    @JsonProperty("preco")
    @Positive(message = "Preco deve ser maior ou igual a zero")
    private Double preco;
}