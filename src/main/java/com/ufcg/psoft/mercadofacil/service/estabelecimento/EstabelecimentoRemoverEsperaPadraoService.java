package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoRemoveRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoDiferenteException;
import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoFuncionarioEsperaException;
import com.ufcg.psoft.mercadofacil.exception.FuncionarioNaoExisteException;
import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Funcionario;
import com.ufcg.psoft.mercadofacil.repository.FuncionarioRepository;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoRemoverEsperaPadraoService implements EstabelecimentoRemoverEsperaService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Override
    public Estabelecimento excluirEspera(EstabelecimentoRemoveRequestDTO estabelecimentoRemoveRequestDTO, Long idFuncionario) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoRemoveRequestDTO.getId()).orElseThrow(EstabelecimentoNaoExisteException::new);
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario).orElseThrow(FuncionarioNaoExisteException::new);
        if (estabelecimentoRemoveRequestDTO.getCodigoAcesso().equals(estabelecimento.getCodigoAcesso())) {
            if(estabelecimento.getEspera().contains(funcionario)) {
                estabelecimento.getEspera().remove(funcionario);
            }
            else {
                throw new EstabelecimentoNaoFuncionarioEsperaException();
            }
        }
        else {
            throw new CodigoAcessoDiferenteException();
        }

        estabelecimentoRepository.save(estabelecimento);
        return estabelecimento;
    }
}
