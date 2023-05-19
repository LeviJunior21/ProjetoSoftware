package com.ufcg.psoft.mercadofacil.exception;

public class EstabelecimentoNaoFuncionarioEsperaException extends MercadoFacilException {
    public EstabelecimentoNaoFuncionarioEsperaException() {
        super("O estabelecimento nao contem esse funcionario na lista de espera!");
    }
}
