package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoRemoveRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoDiferenteException;
import com.ufcg.psoft.mercadofacil.exception.SaborNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoExcluirPadraoService implements EstabelecimentoExcluirService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public void excluir(Long id, EstabelecimentoRemoveRequestDTO estabelecimentoRemoveRequestDTO) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(SaborNaoExisteException::new);
        if (!estabelecimentoRemoveRequestDTO.getCodigoAcesso().equals(estabelecimento.getCodigoAcesso())) {
            throw new CodigoAcessoDiferenteException();
        }
        estabelecimentoRepository.delete(estabelecimento);
    }
}
