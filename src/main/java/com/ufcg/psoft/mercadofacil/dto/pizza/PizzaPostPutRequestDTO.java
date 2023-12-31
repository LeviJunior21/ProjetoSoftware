package com.ufcg.psoft.mercadofacil.dto.pizza;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.dto.valid.pizza.TamanhoPizza;
import com.ufcg.psoft.mercadofacil.model.Sabor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PizzaPostPutRequestDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("nomePizza")
    @NotBlank(message = "Nome eh obrigatorio")
    private String nomePizza;
    @JsonProperty("tamanho")
    @NotBlank(message = "Tamanho da pizza eh obrigatorio")
    @TamanhoPizza
    private String tamanho;
    @JsonProperty("sabores")
    private Set<Sabor> sabor;
    @JsonProperty("disponibilidade")
    @NotBlank(message = "Disponibilidade obrigatorio")
    private String disponibilidade;
}