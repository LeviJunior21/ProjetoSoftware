package com.ufcg.psoft.mercadofacil.dto.funcionario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.dto.valid.CodigoAcesso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioRemoveRequestDTO {
    @JsonProperty("codigoAcesso")
    @CodigoAcesso
    private Integer codigoAcesso;
}
