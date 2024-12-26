package com.bigchatbrasil.modules.cliente.useCases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bigchatbrasil.exceptions.UserNotFoundException;
import com.bigchatbrasil.modules.cliente.entity.ClienteEntity;
import com.bigchatbrasil.modules.cliente.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FindClienteUseCase {

    private final ClienteRepository repository;

    public ClienteEntity execute(UUID id) {
        return this.repository.findById(id).orElseThrow(UserNotFoundException::new);
    }

}
