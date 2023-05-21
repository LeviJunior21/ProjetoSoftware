package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteCriarPadraoService implements ClienteCriarService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public ClienteDTO salvar(ClientePostPutRequestDTO clientePostPutRequestDTO) {
        Cliente cliente = modelMapper.map(clientePostPutRequestDTO, Cliente.class);
        Cliente cliente1 = clienteRepository.save(cliente);
        ClienteDTO clienteDTO = modelMapper.map(cliente1, ClienteDTO.class);
        return clienteDTO;
    }
}
