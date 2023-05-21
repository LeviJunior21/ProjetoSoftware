package com.ufcg.psoft.mercadofacil.service.estabelecimento.pagamento;

public class PagamentoService {

    public Double pagamentoPix(Double valor) {
        MetodoPagamento metodo = new PixDoTipoPagamento(valor);
        return metodo.pagamento();
    }

    public Double pagamentoDebito(Double valor) {
        MetodoPagamento metodo = new DebitoDoTipoPagamento(valor);
        return metodo.pagamento();
    }

    public Double pagamentoCredito(Double valor) {
        MetodoPagamento metodo = new CreditoDoTipoPagamento(valor);
        return metodo.pagamento();
    }
}
