package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoGetRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoDiferenteException;
import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoGetPadraoService implements EstabelecimentoGetService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public Estabelecimento get(EstabelecimentoGetRequestDTO estabelecimentoGetRequestDTO) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoGetRequestDTO.getId()).orElseThrow(EstabelecimentoNaoExisteException::new);
        if (!estabelecimento.getCodigoAcesso().equals(estabelecimentoGetRequestDTO.getCodigoAcesso())) {
            throw new CodigoAcessoDiferenteException();
        }
        return estabelecimento;
    }
}
