package com.ufcg.psoft.mercadofacil.service.estabelecimento.pagamento;

public abstract class MetodoPagamento {
    private Double valor;

    public MetodoPagamento(Double valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Valor null eh invalido");
        }
        else if (valor <= 0) {
            throw new IllegalArgumentException("Valor menor que zero eh invalido");
        }
        this.valor = valor;
    }

    public abstract Double getFatorDesconto();

    public Double pagamento() {
        return this.valor * (1 - this.getFatorDesconto());
    }
}
