package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoDTO;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstabelecimentoListarPadraoService implements EstabelecimentoListarService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<EstabelecimentoDTO> listar() {
        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findAll();
        return estabelecimentos.stream()
                .map(estabelecimento -> modelMapper.map(estabelecimento, EstabelecimentoDTO.class)).collect((Collectors.toList()));
    }
}
