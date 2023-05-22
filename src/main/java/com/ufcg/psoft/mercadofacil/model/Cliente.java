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
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @JsonProperty("nomeCompleto")
    private String nomeCompleto;
    @JsonProperty("enderecoPrincipal")
    private String enderecoPrincipal;
    @JsonProperty("carrinhos")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Pedido> carrinhos;
    @JsonProperty("codigoAcesso")
    private Integer codigoAcesso;
}
