package com.ufcg.psoft.mercadofacil.dto.estabelecimento;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.dto.valid.CodigoAcesso;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoPostPutRequestDTO {

    @JsonProperty("nome")
    @NotBlank
    private String nome;

    @JsonProperty("codigoAcesso")
    @CodigoAcesso
    private Integer codigoAcesso;
}