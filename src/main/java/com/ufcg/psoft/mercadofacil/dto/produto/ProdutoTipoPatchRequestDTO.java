package com.ufcg.psoft.mercadofacil.dto.produto;
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
public class ProdutoTipoPatchRequestDTO {
    @JsonProperty("tipo")
    @NotBlank(message = "Tipo da pizza é obrigatório")
    private String tipo;
}