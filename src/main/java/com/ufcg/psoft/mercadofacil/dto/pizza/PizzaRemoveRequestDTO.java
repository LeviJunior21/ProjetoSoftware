package com.ufcg.psoft.mercadofacil.dto.pizza;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PizzaRemoveRequestDTO {
    @JsonProperty("id")
    private Long id;
}
