package com.ufcg.psoft.mercadofacil.dto.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.model.Sabor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
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
    private List<Sabor> carrinhos;
}
