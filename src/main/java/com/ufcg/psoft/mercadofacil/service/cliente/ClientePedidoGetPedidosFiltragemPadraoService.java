package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.pedido.PedidoDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoGetFiltragemDTO;
import com.ufcg.psoft.mercadofacil.estados.PedidoEmRota;
import com.ufcg.psoft.mercadofacil.estados.PedidoEntregue;
import com.ufcg.psoft.mercadofacil.estados.PedidoPronto;
import com.ufcg.psoft.mercadofacil.exception.ClienteNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Pedido;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientePedidoGetPedidosFiltragemPadraoService implements ClienteGetPedidosFiltragemService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public List<PedidoDTO> getPedidosFiltragem(Long idCliente, ClientePedidoGetFiltragemDTO pedidoGetFiltragemDTO) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNaoExisteException::new);
        List<Pedido> pedidosFiltrados = new ArrayList<>(cliente.getHistoricoPedidos());
        pedidosFiltrados = ordenaFiltragem(pedidosFiltrados, pedidoGetFiltragemDTO);
        return pedidosFiltrados.stream().map(pedido -> modelMapper.map(pedido, PedidoDTO.class)).collect(Collectors.toList());
    }

    private List<Pedido> ordenaFiltragem(List<Pedido> list, ClientePedidoGetFiltragemDTO pedidoGetFiltragemDTO) {
        List<Pedido> copia = new ArrayList<>();

        for (Pedido pedido : list) {
            if (pedidoGetFiltragemDTO.getFiltro().equals("Pronto")
                    && pedido.getPedidoStateNext().getClass().equals(PedidoPronto.class)) {
                copia.add(pedido);
            } else if (pedidoGetFiltragemDTO.getFiltro().equals("EmRota")
                    && pedido.getPedidoStateNext().getClass().equals(PedidoEmRota.class)) {
                copia.add(pedido);
            } else if (pedidoGetFiltragemDTO.getFiltro().equals("Entregue") && pedido.getPedidoStateNext().getClass().equals(PedidoEntregue.class)) {
                copia.add(pedido);
            }
        }
        return copia;
    }
}
