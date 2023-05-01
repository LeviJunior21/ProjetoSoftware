package com.ufcg.psoft.mercadofacil.dto.entregador;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntregadorNomePatchRequestDTO {
    @JsonProperty("nome")
    @NotBlank(message = "Nome obrigatorio")
    private String nome;
    
}
