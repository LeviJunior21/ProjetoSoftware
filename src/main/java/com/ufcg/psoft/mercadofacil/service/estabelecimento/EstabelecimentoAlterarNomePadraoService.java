package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoDiferenteException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoAlterarNomePadraoService implements EstabelecimentoAlterarNomeService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public EstabelecimentoDTO alterarParcialmente(Long id, EstabelecimentoNomePatchRequestDTO estabelecimentoNomePatchRequestDTO) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoExisteException::new);
        if (!estabelecimento.getCodigoAcesso().equals(estabelecimentoNomePatchRequestDTO.getCodigoAcesso())) {
            throw new CodigoAcessoDiferenteException();
        }
        modelMapper.map(estabelecimentoNomePatchRequestDTO, estabelecimento);
        Estabelecimento estabelecimento1 = estabelecimentoRepository.save(estabelecimento);
        EstabelecimentoDTO estabelecimentoDTO = modelMapper.map(estabelecimento1, EstabelecimentoDTO.class);
        return estabelecimentoDTO;
    }
}
