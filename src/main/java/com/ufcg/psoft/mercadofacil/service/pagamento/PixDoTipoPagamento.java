package com.ufcg.psoft.mercadofacil.service.pagamento;

public class PixDoTipoPagamento extends MetodoPagamento {
    public PixDoTipoPagamento(Double valor) {
        super(valor);
    }

    @Override
    public Double getFatorDesconto() {
        return 0.05;
    }
}
