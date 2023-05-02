package com.ufcg.psoft.mercadofacil.dto.lote;

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
public class LoteItensPatchRequestDTO {
    @JsonProperty("numeroDeItens")
    @Positive(message = "Número de itens é obrigatório")
    private Integer numeroDeItens;
}
