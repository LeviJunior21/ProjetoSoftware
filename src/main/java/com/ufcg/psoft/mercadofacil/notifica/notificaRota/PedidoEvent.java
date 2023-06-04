package com.ufcg.psoft.mercadofacil.notifica.notificaRota;

import com.ufcg.psoft.mercadofacil.model.Entregador;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PedidoEvent {
    private PedidoSource notificadorSource;
    private Entregador entregador;
}
