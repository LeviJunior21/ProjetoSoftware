package com.ufcg.psoft.mercadofacil.exception;

public class QuantidadeDeSaboresInvalidaException extends MercadoFacilException{
    public QuantidadeDeSaboresInvalidaException() {
        super("A quantidade de pizzas nao condiz com o tamanho");
    }
}
