package com.ufcg.psoft.mercadofacil.dto.lote;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.model.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotePostPutRequestDTO {
    @JsonProperty("produto")
    @NotBlank(message = "Produto é obrigatorio")
    @NotNull(message = "Produto é obrigatório")
    private Produto produto;

    @JsonProperty("numeroDeItens")
    @Positive(message = "Número de itens é obrigatório")
    private Integer numeroDeItens;
}
