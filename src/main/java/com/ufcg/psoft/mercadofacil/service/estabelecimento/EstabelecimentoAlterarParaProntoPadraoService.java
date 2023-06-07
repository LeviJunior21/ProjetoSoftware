package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostGetRequestDTO;
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

@Service
public class EstabelecimentoAlterarParaProntoPadraoService implements EstabelecimentoAlterarParaProntoService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Override
    public void alterarParaPronto(Long idEstabelecimento, Long idPedido, EstabelecimentoPostGetRequestDTO estabelecimentoPostGetRequestDTO) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(EstabelecimentoNaoExisteException::new);
        if (!estabelecimento.getCodigoAcesso().equals(estabelecimentoPostGetRequestDTO.getCodigoAcesso())) {
            throw new CodigoAcessoDiferenteException();
        }
        estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(idPedido)).findFirst().get().next();
        for (Pedido pedido : estabelecimento.getPedidos()) {
            if (pedido.getId().equals(idPedido)) {
                Cliente cliente = clienteRepository.findById(pedido.getIdCliente()).orElseThrow(ClienteNaoExisteException::new);
                cliente.getHistoricoPedidos().add(pedido);
                clienteRepository.save(cliente);
                break;
            }
        }
        estabelecimentoRepository.save(estabelecimento);
    }
}
