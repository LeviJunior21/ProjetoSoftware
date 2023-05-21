package com.ufcg.psoft.mercadofacil.service.pagamento;

public class CreditoDoTipoPagamento extends MetodoPagamento {

    public CreditoDoTipoPagamento(Double valor) {
        super(valor);
    }

    @Override
    public Double getFatorDesconto() {
        return 0.0;
    }
}
