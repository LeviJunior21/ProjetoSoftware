package com.ufcg.psoft.mercadofacil.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientePedidoGetFiltragemDTO {
    private String filtro;
}
