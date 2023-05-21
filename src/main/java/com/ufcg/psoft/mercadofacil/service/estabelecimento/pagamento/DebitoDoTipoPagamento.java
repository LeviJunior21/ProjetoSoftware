package com.ufcg.psoft.mercadofacil.service.estabelecimento.pagamento;

public class DebitoDoTipoPagamento extends MetodoPagamento {

    public DebitoDoTipoPagamento(Double valor) {
        super(valor);
    }

    @Override
    public Double getFatorDesconto() {
        return 0.025;
    }
}
