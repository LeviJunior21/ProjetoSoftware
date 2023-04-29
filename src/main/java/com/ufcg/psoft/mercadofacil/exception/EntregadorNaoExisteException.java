package com.ufcg.psoft.mercadofacil.exception;

public class EntregadorNaoExisteException extends MercadoFacilException {
    public EntregadorNaoExisteException() {
        super("O estabelecimento consultado não existe!");
    }
}
