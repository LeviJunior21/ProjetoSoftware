package com.ufcg.psoft.mercadofacil.exception;

public class SaborNaoExisteException extends MercadoFacilException {
    public SaborNaoExisteException() {
        super("O produto consultado não existe!");
    }
}
