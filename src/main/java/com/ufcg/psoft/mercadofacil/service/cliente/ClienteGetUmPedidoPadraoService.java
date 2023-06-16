package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.pedido.PedidoDTO;
import com.ufcg.psoft.mercadofacil.exception.ClienteNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Pedido;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteGetUmPedidoPadraoService implements ClienteGetUmPedidoService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public PedidoDTO getUmPedido(Long idCliente, Long idPedido) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNaoExisteException::new);
        Pedido pedido = cliente.getHistoricoPedidos().stream().filter(pedido1 -> pedido1.getId().equals(idPedido)).findFirst().get();
        PedidoDTO pedidoDTO = modelMapper.map(pedido, PedidoDTO.class);
        return pedidoDTO;
    }
}
