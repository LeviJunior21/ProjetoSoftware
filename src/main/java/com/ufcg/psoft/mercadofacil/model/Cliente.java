package com.ufcg.psoft.mercadofacil.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.notifica.ClienteListener;
import com.ufcg.psoft.mercadofacil.notifica.NotificaEvent;
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
@Table(name = "clientes")
public class Cliente implements ClienteListener {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("nomeCompleto")
    private String nomeCompleto;

    @JsonProperty("enderecoPrincipal")
    private String enderecoPrincipal;

    @JsonProperty("carrinho")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Pedido carrinho;

    @JsonProperty("codigoAcesso")
    private Integer codigoAcesso;

    @Override
    public void notifica(NotificaEvent event) {
        System.out.println(this.getNomeCompleto()
                + ", sua pizza de interesse: " + event.getPizza().getNomePizza() + ", esta disponivel\n");
    }
}
