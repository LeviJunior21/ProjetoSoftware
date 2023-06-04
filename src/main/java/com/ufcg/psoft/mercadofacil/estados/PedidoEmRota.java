package com.ufcg.psoft.mercadofacil.estados;

import com.ufcg.psoft.mercadofacil.exception.ClienteNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Pedido;
import com.ufcg.psoft.mercadofacil.notifica.notificaRota.PedidoSource;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoEmRota implements PedidoState{
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Override
    public void next(Pedido pedido) {

        pedido.setStatePedido(new PedidoEntregue());
    }

    @Override
    public void previous(Pedido pedido) {
       // Não tem implementação para esse estado.
    }

    @Override
    public void notifica(Cliente cliente, Estabelecimento estabelecimento) {
        this.notificaClienteQuandoPedidoEMRota(cliente, estabelecimento);
    }

    public void notificaClienteQuandoPedidoEMRota(Cliente cliente, Estabelecimento estabelecimento) {
        Entregador entregador1 = null;
        for (Entregador entregador: estabelecimento.getEntregadores()) {
            if (entregador.isEntregando() == false) {
                entregador1 = entregador;
                entregador.setEntregando(true);
                break;
            }
        }
        if (entregador1 != null) {
            PedidoSource pedidoSource = new PedidoSource();
            pedidoSource.addPedidoDoClienteEmRota(cliente);
            pedidoSource.notificaEmRota(entregador1);
        }
    }
}
