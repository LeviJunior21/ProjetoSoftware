package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoRequestDTO;
import com.ufcg.psoft.mercadofacil.estados.PedidoPronto;
import com.ufcg.psoft.mercadofacil.exception.AlteraPedidoException;
import com.ufcg.psoft.mercadofacil.exception.ClienteNaoExisteException;
import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoDiferenteException;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;

public class ClienteCancelarPedidoPadraoService implements ClienteCancelarPedidoService{

    ClienteRepository clienteRepository;
    @Override
    public ClienteDTO cancelaPedido(Long idCliente, Long idEstabelecimento, ClientePedidoRequestDTO clientePedidoRequestDTO) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNaoExisteException::new);
        if(!cliente.getCodigoAcesso().equals(clientePedidoRequestDTO.getCodigoAcesso())){
            throw new CodigoAcessoDiferenteException();
        }
        if(clientePedidoRequestDTO.getCarrinho().getPedidoStateNext() instanceof PedidoPronto){
            throw new AlteraPedidoException();
        }

        return null;
    }
}
