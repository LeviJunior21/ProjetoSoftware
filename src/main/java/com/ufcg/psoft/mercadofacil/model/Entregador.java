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
@Table(name = "entregadores")
public class Entregador {
    @JsonProperty("codigo")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("codigoAcesso")
    private Integer codigoAcesso;

    @JsonProperty("nome")
    @Column(nullable = false)
    private String nome;

    @JsonProperty("placa")
    @Column(nullable = false)
    private String placa;

    @JsonProperty("veiculo")
    @Column(nullable = false)
    private String veiculo;

    @JsonProperty("cor")
    @Column(nullable = false)
    private String cor;

    @JsonProperty("entregando")
    @Column(nullable = false)
    private boolean entregando;
}
