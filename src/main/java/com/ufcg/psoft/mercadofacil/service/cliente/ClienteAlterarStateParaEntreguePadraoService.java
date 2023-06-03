package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.estados.PedidoEmRota;
import com.ufcg.psoft.mercadofacil.estados.PedidoEntregue;
import com.ufcg.psoft.mercadofacil.exception.*;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ClienteAlterarStateParaEntreguePadraoService implements ClienteAlterarStateParaEntregueService{
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public EstabelecimentoDTO alterarPedido(Long idCliente, Long idEstabelecimento, ClientePedidoPatchRequestDTO clientePedidoPatchRequestDTO) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNaoExisteException::new);
        if (!cliente.getCodigoAcesso().equals(clientePedidoPatchRequestDTO.getCodigoAcesso()))  {
            throw new CodigoAcessoDiferenteException();
        }

        if(!(clientePedidoPatchRequestDTO.getCarrinho().getPedidoStateNext() instanceof PedidoEmRota)){
            throw new AlteraPedidoException();
        } else{
            clientePedidoPatchRequestDTO.getCarrinho().getPedidoStateNext().next(clientePedidoPatchRequestDTO.getCarrinho(), new PedidoEntregue());
        }
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(EstabelecimentoNaoExisteException::new);        
        estabelecimento.getPedidos().add(clientePedidoPatchRequestDTO.getCarrinho());
        Estabelecimento estabelecimento1 = estabelecimentoRepository.save(estabelecimento);
        EstabelecimentoDTO estabelecimentoDTO = modelMapper.map(estabelecimento1, EstabelecimentoDTO.class);
        return estabelecimentoDTO;
    }
}

