package com.ufcg.psoft.mercadofacil.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "produtos")
public class Produto {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @JsonProperty("nome")
    @Column(nullable = false)
    private String nome;
    @JsonProperty("preco")
    @Column(nullable = false)
    private Double preco;
    @JsonProperty("tamanho")
    @Column(nullable = false)
    private String tamanho;
    @JsonProperty("tipo")
    @Column(nullable = false)
    private String tipo;

}
