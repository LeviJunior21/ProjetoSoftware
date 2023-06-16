package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostGetRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.*;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoPrepararPedidoPadraoService implements EstabelecimentoAlterarParaRecebidoService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public void alterarParaRecebido(Long idEstabelecimento, EstabelecimentoPostGetRequestDTO estabelecimentoPostGetRequestDTO, Long idPedido) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(EstabelecimentoNaoExisteException::new);
        if (!estabelecimento.getCodigoAcesso().equals(estabelecimentoPostGetRequestDTO.getCodigoAcesso())) {
            throw new CodigoAcessoDiferenteException();
        }
        estabelecimento.getPedidos().stream().filter(pedido -> pedido.getId().equals(idPedido)).findFirst().get().next();
        estabelecimentoRepository.save(estabelecimento);
    }
}
