package com.ufcg.psoft.mercadofacil.service.cliente;

import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteDTO;
import com.ufcg.psoft.mercadofacil.dto.cliente.ClienteNomePatchRequestDTO;
import com.ufcg.psoft.mercadofacil.exception.ClienteNaoExisteException;
import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoDiferenteException;
import com.ufcg.psoft.mercadofacil.exception.FuncionarioNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteAlterarNomePadraoService implements ClienteAlterarNomeService {

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ClienteDTO alterarParcialmente(ClienteNomePatchRequestDTO clienteNomePatchRequestDTO) {
        Cliente cliente = clienteRepository.findById(clienteNomePatchRequestDTO.getId()).orElseThrow(ClienteNaoExisteException::new);
        if (!cliente.getCodigoAcesso().equals(clienteNomePatchRequestDTO.getCodigoAcesso()))  {
            throw new CodigoAcessoDiferenteException();
        }
        modelMapper.map(clienteNomePatchRequestDTO, cliente);
        Cliente cliente1 = clienteRepository.save(cliente);
        ClienteDTO clienteDTO = modelMapper.map(cliente1, ClienteDTO.class);
        return clienteDTO;
    }
}
