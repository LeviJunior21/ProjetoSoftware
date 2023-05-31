package com.ufcg.psoft.mercadofacil.exception;

public class PizzaNaoEncontradaException extends MercadoFacilException {
    public PizzaNaoEncontradaException() {
        super("Nao ha pizza(s) enviada(s)");
    }
}
