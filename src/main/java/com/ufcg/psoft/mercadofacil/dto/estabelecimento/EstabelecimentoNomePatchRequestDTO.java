package com.ufcg.psoft.mercadofacil.dto.estabelecimento;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.dto.valid.estabelecimento.CodigoAcesso;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoNomePatchRequestDTO {
    @JsonProperty("nome")
    @NotBlank(message = "Nome obrigatorio")
    private String nome;

    @JsonProperty("codigoAcesso")
    @CodigoAcesso
    private Integer codigoAcesso;

}
