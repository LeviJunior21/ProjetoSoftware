package com.ufcg.psoft.mercadofacil.dto.lote;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.model.Produto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoteNomePatchRequestDTO {
    @JsonProperty("produto")
    @NotBlank(message = "Nome é obrigatorio")
    private Produto produto;
}
