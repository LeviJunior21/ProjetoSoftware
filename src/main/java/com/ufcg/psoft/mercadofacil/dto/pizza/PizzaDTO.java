package com.ufcg.psoft.mercadofacil.dto.pizza;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.model.Sabor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PizzaDTO {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("nomePizza")
    @Column(nullable = false)
    private String nomePizza;
    @JsonProperty("tamanho")
    @Column(nullable = false)
    private String tamanho;
    @JsonProperty("sabores")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Sabor> sabor;
    @JsonProperty("disponibilidade")
    @Column(nullable = false)
    private String disponibilidade;
}
