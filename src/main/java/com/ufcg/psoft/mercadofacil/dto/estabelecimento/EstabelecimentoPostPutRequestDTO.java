package com.ufcg.psoft.mercadofacil.dto.estabelecimento;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoPostPutRequestDTO {

    @JsonProperty("nome")
    @NotBlank
    private String nome;

}
