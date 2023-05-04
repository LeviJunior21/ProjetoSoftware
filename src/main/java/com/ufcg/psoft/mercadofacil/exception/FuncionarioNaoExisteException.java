package com.ufcg.psoft.mercadofacil.exception;

public class FuncionarioNaoExisteException extends MercadoFacilException {
    public FuncionarioNaoExisteException() {
        super("O estabelecimento consultado não existe!");
    }
}
