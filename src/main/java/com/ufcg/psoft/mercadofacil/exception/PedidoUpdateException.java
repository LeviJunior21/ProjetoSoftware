package com.ufcg.psoft.mercadofacil.exception;

public class PedidoUpdateException extends MercadoFacilException {
    public PedidoUpdateException() {
        super("Você não tem autorização para alterar o status desse pedido.");
    }
}
