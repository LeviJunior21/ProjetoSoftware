package com.ufcg.psoft.mercadofacil.notifica;

import com.ufcg.psoft.mercadofacil.model.Pizza;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificaEvent {
    private NotificadorSource notificadorSource;
    private Pizza pizza;
}