package com.ufcg.psoft.mercadofacil.dto.funcionario;

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
@Table(name = "funcionarios")
public class FuncionarioDTO {
    @JsonProperty("codigo")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

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

}
