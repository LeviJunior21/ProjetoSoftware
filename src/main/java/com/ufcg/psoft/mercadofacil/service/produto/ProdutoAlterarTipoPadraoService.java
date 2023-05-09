package com.ufcg.psoft.mercadofacil.service.produto;

import com.ufcg.psoft.mercadofacil.dto.produto.ProdutoTipoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.ProdutoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoAlterarTipoPadraoService implements ProdutoAlterarTipoService{
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public Produto alterarParcialmente(Long id, ProdutoTipoPatchRequestDTO produtoTipoPatchRequestDTO) {
        Produto produto = produtoRepository.findById(id).orElseThrow(ProdutoNaoExisteException::new);
        modelMapper.map(produtoTipoPatchRequestDTO, produto);
        return produtoRepository.save(produto);
    }
}