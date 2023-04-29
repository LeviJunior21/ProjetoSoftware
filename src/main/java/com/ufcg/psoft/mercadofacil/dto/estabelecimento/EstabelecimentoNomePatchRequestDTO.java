package com.ufcg.psoft.mercadofacil.dto.estabelecimento;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoNomePatchRequestDTO {
    @JsonProperty("nome")
    @NotBlank(message = "Nome obrigatorio")
    private String nome;
}
