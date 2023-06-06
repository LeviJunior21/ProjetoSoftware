package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoPostDTO;
import com.ufcg.psoft.mercadofacil.estados.PedidoEmPreparo;
import com.ufcg.psoft.mercadofacil.estados.PedidoPronto;
import com.ufcg.psoft.mercadofacil.estados.PedidoRecebido;
import com.ufcg.psoft.mercadofacil.exception.AlteraPedidoException;
import com.ufcg.psoft.mercadofacil.exception.ClienteNaoExisteException;
import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoDiferenteException;
import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Pedido;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class ClienteCancelarPedidoPadraoService implements ClienteCancelarPedidoService{
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Override
    public void cancelaPedido(Long idCliente, Long idPedido, Long idEstabelecimento, ClientePedidoPostDTO clientePedidoPostDTO) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNaoExisteException::new);
        if(!cliente.getCodigoAcesso().equals(clientePedidoPostDTO.getCodigoAcesso())){
            throw new CodigoAcessoDiferenteException();
        }
        if(cliente.getPedido().getPedidoStateNext().getClass().equals(PedidoRecebido.class)
                ||cliente.getPedido().getPedidoStateNext().getClass().equals(PedidoEmPreparo.class)) {
            Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(EstabelecimentoNaoExisteException::new);
            Pedido pedidoRes = estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(idPedido)).findFirst().get();
            ArrayList<Pedido> pedidos =  new ArrayList<>(estabelecimento.getPedidos());
            for (int i = 0; i < pedidos.size(); i++) {
                if (pedidos.get(i).equals(pedidoRes)) {
                    pedidos.remove(i);
                    break;
                }
            }
            Set<Pedido> pedidoSet = new HashSet<>(pedidos);
            estabelecimento.setPedidos(pedidoSet);
            estabelecimentoRepository.save(estabelecimento);
            cliente.setPedido(null);
            clienteRepository.save(cliente);
        } else {
            throw new AlteraPedidoException();
        }
    }
}
