package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteEnderecoPatchRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.ClienteNaoExisteException;
import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoDiferenteException;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteAlterarEnderecoPadraoService implements ClienteAlterarEnderecoService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ClienteDTO alterarParcialmente(Long id, ClienteEnderecoPatchRequestDTO clienteEnderecoPatchRequestDTO) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(ClienteNaoExisteException::new);
        if (!cliente.getCodigoAcesso().equals(clienteEnderecoPatchRequestDTO.getCodigoAcesso()))  {
            throw new CodigoAcessoDiferenteException();
        }
        modelMapper.map(clienteEnderecoPatchRequestDTO, cliente);
        Cliente cliente1 = clienteRepository.save(cliente);
        ClienteDTO clienteDTO = modelMapper.map(cliente1, ClienteDTO.class);
        return clienteDTO;
    }
}
