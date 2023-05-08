package com.ufcg.psoft.mercadofacil.exception;

public class CodigoAcessoDiferenteException extends MercadoFacilException{

    public CodigoAcessoDiferenteException() {
        super("O codigo de acesso eh diferente!");
    }

}
