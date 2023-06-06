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
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteCancelarPedidoPadraoService implements ClienteCancelarPedidoService{
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Override
    public void cancelaPedido(Long idCliente, Long idEstabelecimento, ClientePedidoPostDTO clientePedidoPostDTO) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNaoExisteException::new);
        if(!cliente.getCodigoAcesso().equals(clientePedidoPostDTO.getCodigoAcesso())){
            throw new CodigoAcessoDiferenteException();
        }
        if(!cliente.getPedido().getPedidoStateNext().equals(PedidoEmPreparo.class) ||
                !cliente.getPedido().getPedidoStateNext().equals(PedidoRecebido.class)){
            throw new AlteraPedidoException();
        }
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(EstabelecimentoNaoExisteException::new);
        estabelecimento.getPedidos().remove(cliente.getPedido());
        estabelecimentoRepository.save(estabelecimento);
        cliente.setPedido(null);
        clienteRepository.save(cliente);
    }
}
