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
public class ProdutoNomePatchRequestDTO {
    @JsonProperty("nome")
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
}