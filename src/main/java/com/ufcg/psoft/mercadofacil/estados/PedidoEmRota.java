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

public class PedidoEmRota implements PedidoState{
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Override
    public void next(Pedido pedido, PedidoState state) {
        pedido.setStatePedido(new PedidoEntregue());
    }

    @Override
    public void previous(Pedido pedido) {
       // Não tem implementação para esse estado.
    }

    @Override
    public void notifica(Long idCliente, String message) {
        // Busca cliente no banco
        // Chama a funcao notifica no cliente passando a mensagem
    }

    public void notificaClienteQuandoPedidoEMRota(Long idCliente, Long idEstabelecimento) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNaoExisteException::new);
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(ClienteNaoExisteException::new);
        Entregador entregador1 = new Entregador();
        for (Entregador entregador: estabelecimento.getEntregadores()) {
            if (entregador.isEntregando() == false) {
                entregador1 = entregador;
            }
        }
        PedidoSource pedidoSource = new PedidoSource();
        pedidoSource.notificaEmRota(entregador1);
    }
}
