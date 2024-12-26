package com.bigchatbrasil.modules.cliente.useCases;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bigchatbrasil.exceptions.UserFoundException;
import com.bigchatbrasil.modules.cliente.dto.CreateClienteRequestDTO;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;
import com.bigchatbrasil.modules.cliente.vo.Conta;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateClienteUseCase {

    private final ClienteRepository repository;

    public ClienteEntity execute(CreateClienteRequestDTO createClienteRequestDTO) {
        repository.findByEmailOrCpfResponsavelOrCnpj(createClienteRequestDTO.email(),
                        createClienteRequestDTO.cpfResponsavel(),
                        createClienteRequestDTO.cnpj())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        Conta conta = new Conta();
        ClienteEntity clienteEntity = new ClienteEntity();

        BeanUtils.copyProperties(createClienteRequestDTO, clienteEntity);
        BeanUtils.copyProperties(createClienteRequestDTO.conta(), conta);
        clienteEntity.setConta(conta);

        return repository.save(clienteEntity);
    }

}
