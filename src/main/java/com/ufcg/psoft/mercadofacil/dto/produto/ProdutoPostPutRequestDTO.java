package com.ufcg.psoft.mercadofacil.dto.produto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoPostPutRequestDTO {
    @JsonProperty("nome")
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    @JsonProperty("preco")
    @Positive(message = "Preço deve ser maior ou igual a zero")
    private Double preco;
    @JsonProperty("tamanho")
    @NotBlank(message = "Tamanho da pizza é obrigatório")
    private String tamanho;
    @JsonProperty("tipo")
    @NotBlank(message = "Tipo da pizza é obrigatório")
    private String tipo;
}