package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.estabelecimento.EstabelecimentoMensagemGetDTO;
import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.mercadofacil.exception.PizzaNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.model.Pizza;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoAlterarParaIndisponivelPadraoService implements EstabelecimentoAlterarDisponibilidadeService{
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Override
    public EstabelecimentoMensagemGetDTO alterarDisponibilidade(Long idPizza, Long idEstabelecimento) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(idEstabelecimento).orElseThrow(EstabelecimentoNaoExisteException::new);
        Pizza pizza = estabelecimento.getCardapio().stream()
                .filter(elem -> elem.getId().equals(idPizza)).findFirst().orElseThrow(PizzaNaoExisteException::new);
        EstabelecimentoMensagemGetDTO estabelecimentoMensagemGetDTO = new EstabelecimentoMensagemGetDTO();
        estabelecimentoMensagemGetDTO.setMensagem("");
        if (pizza.getDisponibilidade().equals("indisponivel")) {
            estabelecimentoRepository.save(estabelecimento);
            return estabelecimentoMensagemGetDTO;
        }
        pizza.setDisponibilidade("indisponivel");
        estabelecimento.getCardapio().add(pizza);
        estabelecimentoRepository.save(estabelecimento);
        return estabelecimentoMensagemGetDTO;
    }
}
