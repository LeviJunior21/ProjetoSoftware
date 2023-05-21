package com.ufcg.psoft.mercadofacil.service.pagamento;

public class DebitoDoTipoPagamento extends MetodoPagamento {
    public DebitoDoTipoPagamento(Double valor) {
        super(valor);
    }

    @Override
    public Double getFatorDesconto() {
        return 0.025;
    }
}
