package com.ufcg.psoft.mercadofacil.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.notifica.notificaInteresse.ClienteListener;
import com.ufcg.psoft.mercadofacil.notifica.notificaInteresse.NotificaEvent;
import com.ufcg.psoft.mercadofacil.notifica.notificaRota.PedidoEvent;
import com.ufcg.psoft.mercadofacil.notifica.notificaRota.PedidoListener;
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
public class Cliente implements ClienteListener, PedidoListener {
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
    private Pedido pedido;

    @JsonProperty("codigoAcesso")
    private Integer codigoAcesso;

    @Override
    public void notifica(NotificaEvent event) {
        System.out.println(this.getNomeCompleto()
                + ", sua pizza de interesse: " + event.getPizza().getNomePizza() + ", esta disponivel\n");
    }

    @Override
    public void notificaPedidoEmRota(PedidoEvent event) {
        System.out.println("Cliente: " + this.getNomeCompleto() + ", seu pedido está em rota e o nome do entregador é: "
                + event.getEntregador().getNome() + " e o seu veículo é: " + event.getEntregador().getVeiculo() + ", placa: "
                + event.getEntregador().getPlaca() + " e cor do veículo: " + event.getEntregador().getCor() + ".");
    }
}
