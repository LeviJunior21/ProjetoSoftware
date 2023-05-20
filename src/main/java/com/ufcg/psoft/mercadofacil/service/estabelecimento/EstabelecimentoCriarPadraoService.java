package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoDiferenteException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoCriarPadraoService implements EstabelecimentoCriarService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public EstabelecimentoDTO salvar(EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO) {
        Estabelecimento estabelecimento = modelMapper.map(estabelecimentoPostPutRequestDTO, Estabelecimento.class);
        estabelecimentoRepository.save(estabelecimento);
        EstabelecimentoDTO estabelecimentoDTO = modelMapper.map(estabelecimento, EstabelecimentoDTO.class);
        return estabelecimentoDTO;
    }
}
