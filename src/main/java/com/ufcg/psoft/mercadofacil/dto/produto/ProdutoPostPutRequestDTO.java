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
    @NotBlank(message = "Nome eh obrigatorio")
    private String nome;
    @JsonProperty("preco")
    @Positive(message = "Preço deve ser maior ou igual a zero")
    private Double preco;
    @JsonProperty("tamanho")
    @NotBlank(message = "Tamanho da pizza eh obrigatorio")
    private String tamanho;
    @JsonProperty("tipo")
    @NotBlank(message = "Tipo da pizza eh obrigatorio")
    private String tipo;
}