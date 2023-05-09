package com.ufcg.psoft.mercadofacil.service.produto;

import com.ufcg.psoft.mercadofacil.dto.produto.ProdutoTamanhoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.ProdutoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoAlterarTamanhoPadraoService implements ProdutoAlterarTamanhoService{
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public Produto alterarParcialmente(Long id, ProdutoTamanhoPatchRequestDTO produtoTamanhoPatchRequestDTO) {
        Produto produto = produtoRepository.findById(id).orElseThrow(ProdutoNaoExisteException::new);
        modelMapper.map(produtoTamanhoPatchRequestDTO, produto);
        return produtoRepository.save(produto);
    }
}