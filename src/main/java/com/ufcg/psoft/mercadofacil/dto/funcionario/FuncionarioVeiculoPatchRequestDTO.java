package com.ufcg.psoft.mercadofacil.dto.funcionario;

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
public class FuncionarioVeiculoPatchRequestDTO {
    @JsonProperty("veiculo")
    @NotBlank(message = "Veiculo obrigatorio")
    private String veiculo;
}
