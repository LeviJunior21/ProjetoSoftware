package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoRequestDTO;
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
public class ClienteSolicitarPedidoPadraoService implements ClienteSolicitarPedidoService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public void solicitar(Long idCliente, Long idEstabelecimento,  ClientePedidoRequestDTO clientePedidoRequestDTO) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNaoExisteException::new);
        if (!cliente.getCodigoAcesso().equals(clientePedidoRequestDTO.getCodigoAcesso())) {
            throw new CodigoAcessoDiferenteException();
        }
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(EstabelecimentoNaoExisteException::new);
        estabelecimento.getPedidos().add(clientePedidoRequestDTO.getCarrinho());
    }
}
