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
@Table(name = "carrinhos")
public class Carrinho {
    @JsonProperty("lote")
    @ManyToOne
    private Lote lote;

    @JsonProperty("id")
    @Id
    private Long id;

    @JsonProperty("lote")
    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Lote> lotes;
}
