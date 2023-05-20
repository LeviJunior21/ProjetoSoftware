package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteGetRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.ClienteNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteBuscarPadraoService implements ClienteBuscarService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Cliente get(ClienteGetRequestDTO clienteGetRequestDTO) {
        Cliente cliente = clienteRepository.findById(clienteGetRequestDTO.getId()).orElseThrow(ClienteNaoExisteException::new);
        if (!cliente.getCodigoAcesso().equals(clienteGetRequestDTO.getCodigoAcesso())) {
            throw new ClienteNaoExisteException();
        }
        return cliente;
    }
}
