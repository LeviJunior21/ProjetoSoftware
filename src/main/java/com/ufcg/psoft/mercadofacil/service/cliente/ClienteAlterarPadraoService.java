package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.ClienteNaoExisteException;
import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoDiferenteException;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteAlterarPadraoService implements ClienteAlterarService {

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Cliente alterar(ClientePostPutRequestDTO clientePostPutRequestDTO) {
        Cliente cliente = clienteRepository.findById(clientePostPutRequestDTO.getId()).orElseThrow(ClienteNaoExisteException::new);
        if (!cliente.getCodigoAcesso().equals(clientePostPutRequestDTO.getCodigoAcesso()))  {
            throw new CodigoAcessoDiferenteException();
        }
        modelMapper.map(clientePostPutRequestDTO, cliente);
        return clienteRepository.save(cliente);
    }
}
