package com.ufcg.psoft.mercadofacil.service.sabor;

import com.ufcg.psoft.mercadofacil.exception.SaborNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Sabor;
import com.ufcg.psoft.mercadofacil.repository.SaborRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SaborListarPadraoService implements SaborListarService {

    @Autowired
    SaborRepository saborRepository;

    @Override
    public List<Sabor> listar(Long id) {
        if(id!=null && id > 0) {
            saborRepository.findById(id).orElseThrow(SaborNaoExisteException::new);
        }
        return saborRepository.findAll();
    }
}
