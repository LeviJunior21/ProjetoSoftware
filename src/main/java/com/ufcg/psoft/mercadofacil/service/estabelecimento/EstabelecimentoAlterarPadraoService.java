package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.ProdutoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoAlterarPadraoService implements EstabelecimentoAlterarService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Estabelecimento alterar(Long id, EstabelecimentoNomePatchRequestDTO estabelecimentoNomePatchRequestDTO) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(ProdutoNaoExisteException::new);
        modelMapper.map(estabelecimentoNomePatchRequestDTO, estabelecimento);
        return estabelecimentoRepository.save(estabelecimento);
    }
}