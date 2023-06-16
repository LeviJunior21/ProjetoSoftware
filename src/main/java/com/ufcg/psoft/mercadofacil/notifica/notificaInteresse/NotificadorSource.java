package com.ufcg.psoft.mercadofacil.notifica.notificaInteresse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Pizza;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "notificador")
public class NotificadorSource {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @JsonProperty("cliente_listeners")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Map<Cliente, Pizza> clienteListeners = new HashMap<>();

    public void notificaDisponivel(Pizza pizza) {
        NotificaEvent event = new NotificaEvent(this, pizza);
        this.fireNotificacao(event);
    }

    public synchronized void addInteresse(Cliente cliente, Pizza pizza) {
        this.clienteListeners.put(cliente, pizza);
    }

    public synchronized void removeInteresse(Cliente cliente) {
        this.clienteListeners.remove(cliente);
    }

    private void fireNotificacao(NotificaEvent event) {
        Map<Cliente, Pizza> copia;
        synchronized (this) {
            //copia = (Map<Cliente, Pizza>) ((HashMap<Cliente, Pizza>) clienteListeners).clone();
            copia = new HashMap<>(clienteListeners);
        }
        for (var entry : copia.entrySet()) {
            if (entry.getValue().equals(event.getPizza())) {
                entry.getKey().notifica(event);
            }
        }
    }

    @Override
    public String toString() {
        return clienteListeners.toString();
    }
}
