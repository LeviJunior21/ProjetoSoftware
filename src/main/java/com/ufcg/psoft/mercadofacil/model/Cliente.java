package com.ufcg.psoft.mercadofacil.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @JsonProperty("cpf")
    private Long cpf;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("idade")
    private Integer idade;
    @JsonProperty("endereco")
    private String endereco;
    @JsonProperty("carrinhos")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Produto> carrinhos;
}
