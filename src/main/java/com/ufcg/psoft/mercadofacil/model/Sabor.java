package com.ufcg.psoft.mercadofacil.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sabores")
public class Sabor {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @JsonProperty("nomeSabor")
    @Column(nullable = false)
    private String nomeSabor;
    @JsonProperty("preco")
    @Column(nullable = false)
    private Double preco;
    @JsonProperty("tipo")
    @Column(nullable = false)
    private String tipo;

}
