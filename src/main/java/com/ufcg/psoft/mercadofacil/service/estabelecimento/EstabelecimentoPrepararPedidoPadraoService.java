package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostGetRequestDTO;
import com.ufcg.psoft.mercadofacil.estados.CriandoPedido;
import com.ufcg.psoft.mercadofacil.estados.PedidoEmPreparo;
import com.ufcg.psoft.mercadofacil.exception.*;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Pedido;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoPrepararPedidoPadraoService implements EstabelecimentoPrepararPedidoService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public EstabelecimentoDTO preparar(Long idEstabelecimento, EstabelecimentoPostGetRequestDTO estabelecimentoPostGetRequestDTO, Long idPedido) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(EstabelecimentoNaoExisteException::new);
        if (!estabelecimento.getCodigoAcesso().equals(estabelecimentoPostGetRequestDTO.getCodigoAcesso())) {
            throw new CodigoAcessoDiferenteException();
        }
        Pedido pedidoEncontrado = estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(idPedido)).findFirst().orElseThrow(PedidoNaoExisteException::new);
        if (!pedidoEncontrado.getPedidoStateNext().getClass().equals(CriandoPedido.class)) {
            throw new PedidoUpdateException();
        }
        pedidoEncontrado.next();
        if (pedidoEncontrado.getPedidoStateNext().getClass().equals(PedidoEmPreparo.class)) {
            Cliente cliente = clienteRepository.findById(pedidoEncontrado.getIdCliente()).orElseThrow(ClienteNaoExisteException::new);
            // cliente.notifica(cliente.getNomeCompleto() + ", o seu pedido está sendo preparado pelo estabelecimento " + estabelecimento.getNome()+".");
        }
        EstabelecimentoDTO estabelecimentoDTO = modelMapper.map(estabelecimento, EstabelecimentoDTO.class);
        return estabelecimentoDTO;
    }
}
