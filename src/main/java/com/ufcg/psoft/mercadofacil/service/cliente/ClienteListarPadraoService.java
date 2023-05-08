package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteListarPadraoService implements ClienteListarService {
    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public List<Cliente> listar(Long id) {
        return clienteRepository.findAll();
    }
}
