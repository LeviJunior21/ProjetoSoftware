package com.ufcg.psoft.mercadofacil.service.sabor;

import com.ufcg.psoft.mercadofacil.dto.sabor.SaborTipoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.SaborNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Sabor;
import com.ufcg.psoft.mercadofacil.repository.SaborRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborAlterarTipoPadraoService implements SaborAlterarTipoService {
    @Autowired
    SaborRepository saborRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public Sabor alterarParcialmente(Long id, SaborTipoPatchRequestDTO saborTipoPatchRequestDTO) {
        Sabor sabor = saborRepository.findById(id).orElseThrow(SaborNaoExisteException::new);
        modelMapper.map(saborTipoPatchRequestDTO, sabor);
        return saborRepository.save(sabor);
    }
}