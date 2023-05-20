package com.ufcg.psoft.mercadofacil.exception;

public class PizzaNaoExisteException extends MercadoFacilException{
    public PizzaNaoExisteException() {
        super("A pizza consultada não existe!");
    }
}
