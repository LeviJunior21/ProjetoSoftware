package com.ufcg.psoft.mercadofacil.exception;

public class PedidoNaoExisteException extends MercadoFacilException {
    public PedidoNaoExisteException() {
        super("O pedido consultado não existe!");
    }
}