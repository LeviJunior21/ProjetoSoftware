package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.model.Entregador;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Funcionario;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoAceitarPadraoService implements EstabelecimentoAceitarService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void aceitar(Long estabelecimentoId, Long funcionarioId) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).get();
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId).get();
        Entregador entregador = new Entregador();
        modelMapper.map(funcionario, entregador);
        estabelecimento.getEntregadores().add(entregador);
        estabelecimentoRepository.save(estabelecimento);
    }
}
