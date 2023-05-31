package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.calculadora.CalculaPrecoPizzas;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePedidoRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.exception.*;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import com.ufcg.psoft.mercadofacil.service.cliente.ClienteSolicitarPedidoService;
import com.ufcg.psoft.mercadofacil.service.estabelecimento.pagamento.PagamentoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ClienteSolicitarPedidoPadraoService implements ClienteSolicitarPedidoService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ModelMapper modelMapper;
    CalculaPrecoPizzas calculadora = new CalculaPrecoPizzas();

    PagamentoService pagamentoService = new PagamentoService();

    @Override
    public EstabelecimentoDTO solicitar(Long idCliente, Long idEstabelecimento, ClientePedidoRequestDTO clientePedidoRequestDTO) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNaoExisteException::new);
        if (!cliente.getCodigoAcesso().equals(clientePedidoRequestDTO.getCodigoAcesso())) {
            throw new CodigoAcessoDiferenteException();
        }
        else if (clientePedidoRequestDTO.getCarrinho().getPizzas().size() == 0) {
            throw new PizzaNaoEncontradaException();
        }
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(EstabelecimentoNaoExisteException::new);
        if(clientePedidoRequestDTO == null || clientePedidoRequestDTO.equals("")){
            clientePedidoRequestDTO.setEndereco(cliente.getEnderecoPrincipal());
        }
        Double valorTotal = calculadora.calculaPrecoPizzas(clientePedidoRequestDTO.getCarrinho().getPizzas());
        if(clientePedidoRequestDTO.getMetodoPagamento().equals("PIX")){
            valorTotal = pagamentoService.pagamentoPix(valorTotal);
        }
        else if(clientePedidoRequestDTO.getMetodoPagamento().equals("DEBITO")){
            valorTotal = pagamentoService.pagamentoDebito(valorTotal);
        }
        else if(clientePedidoRequestDTO.getMetodoPagamento().equals("CREDITO")){
            valorTotal = pagamentoService.pagamentoCredito(valorTotal);
        }
        else{
            throw new MetodoPagamentoException();
        }
        clientePedidoRequestDTO.getCarrinho().setValorPedido(valorTotal);
        estabelecimento.getPedidos().add(clientePedidoRequestDTO.getCarrinho());
        Estabelecimento estabelecimento1 = estabelecimentoRepository.save(estabelecimento);
        EstabelecimentoDTO estabelecimentoDTO = modelMapper.map(estabelecimento1, EstabelecimentoDTO.class);
        return estabelecimentoDTO;
    }
}
