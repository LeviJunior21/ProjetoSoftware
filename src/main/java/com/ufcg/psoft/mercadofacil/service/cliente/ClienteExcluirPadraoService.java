package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteRemoveRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.ClienteNaoExisteException;
import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoDiferenteException;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteExcluirPadraoService implements ClienteExcluirService {
    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public void excluir(ClienteRemoveRequestDTO clienteRemoveRequestDTO) {
        Cliente cliente = clienteRepository.findById(clienteRemoveRequestDTO.getId()).orElseThrow(ClienteNaoExisteException::new);
        if (!cliente.getCodigoAcesso().equals(clienteRemoveRequestDTO.getCodigoAcesso())) {
            throw new CodigoAcessoDiferenteException();
        }
        clienteRepository.delete(cliente);
    }
}
