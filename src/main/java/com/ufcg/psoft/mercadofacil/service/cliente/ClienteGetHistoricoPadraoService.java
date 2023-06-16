package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.pedido.PedidoDTO;
import com.ufcg.psoft.mercadofacil.estados.PedidoEmRota;
import com.ufcg.psoft.mercadofacil.estados.PedidoPronto;
import com.ufcg.psoft.mercadofacil.exception.ClienteNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Pedido;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteGetHistoricoPadraoService implements ClienteGetHistoricoService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<PedidoDTO> getHistorico(Long idCliente, Long idEstabelecimento) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNaoExisteException::new);
        List<Pedido> historico = new ArrayList<>(cliente.getHistoricoPedidos());
        historico = ordena(historico, idEstabelecimento);
        return historico.stream().map(pedido -> modelMapper.map(pedido, PedidoDTO.class)).collect(Collectors.toList());
    }

    private List<Pedido> ordena(List<Pedido> list, Long idEstabelecimento) {
        List<Pedido> copia = new ArrayList<>();

        for (Pedido pedido : list) {
            if ((pedido.getPedidoStateNext().getClass().equals(PedidoPronto.class)
                    || pedido.getPedidoStateNext().getClass().equals(PedidoEmRota.class))
                    && idEstabelecimento.equals(pedido.getIdEstabelecimento())) {
                copia.add(pedido);
            }
        }
        for (Pedido pedido : list) {
            if (!pedido.getPedidoStateNext().getClass().equals(PedidoPronto.class)
                    && !pedido.getPedidoStateNext().getClass().equals(PedidoEmRota.class)
                    && idEstabelecimento.equals(pedido.getIdEstabelecimento())) {
                copia.add(pedido);
            }
        }
        return copia;
    }
}
