package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoRemoveRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.ProdutoNaoExisteException;
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
    public void excluir(EstabelecimentoRemoveRequestDTO estabelecimentoRemoveRequestDTO) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoRemoveRequestDTO.getId()).orElseThrow(ProdutoNaoExisteException::new);
        if (estabelecimentoRemoveRequestDTO.getCodigoAcesso().equals(estabelecimento.getCodigoAcesso())) {
            estabelecimentoRepository.delete(estabelecimento);
        }
    }
}
